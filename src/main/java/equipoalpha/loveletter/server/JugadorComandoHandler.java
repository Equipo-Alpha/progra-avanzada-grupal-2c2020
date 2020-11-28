package equipoalpha.loveletter.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import equipoalpha.loveletter.carta.CartaTipo;
import equipoalpha.loveletter.common.MensajeNetwork;
import equipoalpha.loveletter.common.MensajeTipo;
import equipoalpha.loveletter.jugador.JugadorIA;
import equipoalpha.loveletter.partida.Sala;
import equipoalpha.loveletter.partida.eventos.EventosPartida;
import equipoalpha.loveletter.util.JsonUtils;

import java.util.Optional;

public class JugadorComandoHandler {
    // cada uno de los handlers de los comandos del jugador

    // --- handlers de antes del menu principal
    public void onNuevoNombre(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        JsonObject json = mensaje.getMensaje();
        String nombre = JsonUtils.getString(json, "nombre");
        jugadorServer.setNombre(nombre);
        jugadorServer.getListener().setName(nombre);
        jugadorServer.sincronizar(); // se sincroniza
        JsonObject respuesta = new JsonObject();
        respuesta.addProperty("tipo", true); // lo setteo correctamente
        respuesta.addProperty("id", 1); // 1 es el id del tipo de respuesta
        jugadorServer.getListener().send(MensajeTipo.Confirmacion, respuesta);
    }

    // --- handlers del menu principal
    public void onReqSala(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        // aca el jugador clickeo unirse a una sala
        // el servidor debe enviarle una lista de las salas disponibles
        // solo es necesario los nombres, la cantidad de jugadores y si comenzo la partida
        JsonArray array = new JsonArray();
        for (Sala sala : LoveLetterServidor.getINSTANCE().getSalas()) {
            JsonObject salaInfo = new JsonObject();
            salaInfo.addProperty("nombre", sala.nombre);
            salaInfo.addProperty("jugadores", sala.jugadores.size());
            salaInfo.addProperty("inicio", sala.partida != null);
            array.add(salaInfo);
        }
        JsonObject salas = new JsonObject();
        salas.add("lista", array);
        jugadorServer.getListener().send(MensajeTipo.ListaSala, salas);
    }

    public void onUnirseSala(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        // aca el jugador selecciono la sala a la que quiere unirse
        // el servidor debe verificar que la sala que selecciono es valida
        // si es valida debe unirlo y enviar los siguientes mensajes:
        // debe mandarle un mensaje de confirmacion al jugador
        // debe mandar mensajes de sincronizacion a los demas jugadores
        JsonObject json = mensaje.getMensaje();
        String nombre = JsonUtils.getString(json, "nombre");
        Sala sala = LoveLetterServidor.getINSTANCE().getSalaPorNombre(nombre);

        JsonObject respuesta = new JsonObject();
        if (sala == null || !sala.agregarJugador(jugadorServer)) {
            respuesta.addProperty("tipo", false); // sala invalida
            respuesta.addProperty("id", 2); // 2 es el id del tipo de respuesta
            jugadorServer.getListener().send(MensajeTipo.Confirmacion, respuesta);
            return;
        }

        jugadorServer.sincronizar();
        for (JugadorServer jugadores : sala.jugadores) {
            jugadores.sincronizarSala();
        }

        respuesta.addProperty("tipo", true); // lo setteo correctamente
        respuesta.addProperty("id", 2); // 2 es el id del tipo de respuesta
        jugadorServer.getListener().send(MensajeTipo.Confirmacion, respuesta);
    }

    public void onCrearSala(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        // aca el jugador clickeo crear sala
        // el servidor debe crear la sala con el jugador como creador
        // y debe responderle un mensaje de confirmacion y de sincronizacion
        jugadorServer.crearSala("Sala de " + jugadorServer.nombre);
        jugadorServer.sincronizar();
        jugadorServer.sincronizarSala();
        JsonObject respuesta = new JsonObject();
        respuesta.addProperty("tipo", true); // lo setteo correctamente
        respuesta.addProperty("id", 3); // 2 es el id del tipo de respuesta
        jugadorServer.getListener().send(MensajeTipo.Confirmacion, respuesta);
    }

    // --- handlers de la sala
    public void onAgregarBot(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        // aca el jugador clickeo agregar bot
        // solo se puede permitir 1 bot por sala
        // el servidor debe agregar al bot y sincronizar a los demas jugadores
        if (!jugadorServer.salaActual.tieneBot) {
            JugadorIA bot = new JugadorIA("SrBot");
            bot.unirseASala(jugadorServer.salaActual);
            jugadorServer.salaActual.tieneBot = true;
            for (JugadorServer jugadores : jugadorServer.salaActual.jugadores) {
                jugadores.sincronizarSala();
            }
        } else {
            Optional<JugadorServer> bot = jugadorServer.salaActual.jugadores.stream().filter(js -> js instanceof JugadorIA).findFirst();
            bot.ifPresent(JugadorServer::salirSala); // si el bot ya esta que salga de la sala
            jugadorServer.salaActual.tieneBot = false;
            for (JugadorServer jugadores : jugadorServer.salaActual.jugadores) {
                jugadores.sincronizarSala();
            }
        }
    }

    public void onConfigurarSala(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        // aca el jugador confirmo sus ajustes de sala
        // el servidor debe verificar que las configuraciones son correctas y aplicarlas
        // despues se envia un mensaje de sincronizacion
        JsonObject json = mensaje.getMensaje();
        int cantSimbolos = JsonUtils.getInt(json, "simbolos");
        String nombreJugador = JsonUtils.getString(json, "jugadorMano");
        JugadorServer jugadorMano = null;
        for (JugadorServer jugador : jugadorServer.salaActual.jugadores) {
            if (jugador.nombre.equals(nombreJugador)) {
                jugadorMano = jugador;
            }
        }
        jugadorServer.salaActual.setCantSimbolosAfecto(cantSimbolos);
        jugadorServer.salaActual.setJugadorMano(jugadorMano);
        jugadorServer.sincronizarSala();
    }

    public void onPartidaEmpezada(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        jugadorServer.iniciarPartida();
    }

    public void onConfirmarInicio(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        JsonObject objMensaje = mensaje.getMensaje();
        if (!JsonUtils.hasElement(objMensaje, "check"))
            return;

        if (JsonUtils.getBoolean(objMensaje, "check")) {
            jugadorServer.confirmarInicio();
        }
    }

    public void onCancelarInicio(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        JsonObject objMensaje = mensaje.getMensaje();
        if (!JsonUtils.hasElement(objMensaje, "check"))
            return;

        if (JsonUtils.getBoolean(objMensaje, "check")) {
            jugadorServer.cancelarInicio();
        }
    }

    public void onSalirSala(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        JsonObject objMensaje = mensaje.getMensaje();
        if (!JsonUtils.hasElement(objMensaje, "check"))
            return;

        if (JsonUtils.getBoolean(objMensaje, "check")) {
            jugadorServer.salirSala();
        }
    }

    // --- handlers de la partida
    public void onDescartarCarta1(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        JsonObject objMensaje = mensaje.getMensaje();
        if (!JsonUtils.hasElement(objMensaje, "CartaDescartada"))
            return; // mensaje invalido

        //JsonObject cartaTipo = JsonUtils.getObject(objMensaje, "CartaDescartada");

        CartaTipo descarte = (new Gson()).fromJson(objMensaje.get("CartaDescartada"), CartaTipo.class);

        if (jugadorServer.carta1.getTipo() == descarte) {
            jugadorServer.descartarCarta1();
        }
    }

    public void onDescartarCarta2(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        JsonObject objMensaje = mensaje.getMensaje();
        if (!JsonUtils.hasElement(objMensaje, "CartaDescartada"))
            return; // mensaje invalido

        CartaTipo descarte = (new Gson()).fromJson(objMensaje.get("CartaDescartada"), CartaTipo.class);

        if (jugadorServer.carta2.getTipo() == descarte) {
            jugadorServer.descartarCarta2();
        }
    }

    public void onElegirJugador(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        JsonObject objMensaje = mensaje.getMensaje();
        if (!JsonUtils.hasElement(objMensaje, "JugadorElegido"))
            return; // mensaje invalido

        String nombre = JsonUtils.getString(objMensaje, "JugadorElegido");

        JugadorServer elegido = null;
        for (JugadorServer jugadores : jugadorServer.rondaJugando.jugadoresEnLaRonda) {
            if (jugadores.nombre.equals(nombre)) {
                elegido = jugadores;
            }
        }
        if (elegido == null)
            return;
        jugadorServer.elegirJugador(elegido);
    }

    public void onAdivinarCarta(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        JsonObject objMensaje = mensaje.getMensaje();
        if (!JsonUtils.hasElement(objMensaje, "CartaAdivinada"))
            return; // mensaje invalido

        CartaTipo cartaAdivinada = (new Gson()).fromJson(objMensaje.get("CartaAdivinada"), CartaTipo.class);

        jugadorServer.elegirCarta(cartaAdivinada);
    }

    public void onTerminarDeVer(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        jugadorServer.terminarDeVer();
    }

    public void onContinuarComienzo(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        // al comenzar la ronda avisa que esta listo para jugar
        // ver EventoObservado
        if (jugadorServer.salaActual == null)
            return; // mensaje invalido
        jugadorServer.salaActual.eventos.removerObservador(EventosPartida.COMIENZORONDA, jugadorServer);
    }

    public void onContinuarFin(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        // al finalizar la ronda clickea en continuar para seguir
        // ver EventoObservado
        if (jugadorServer.salaActual == null)
            return; // mensaje invalido
        jugadorServer.salaActual.eventos.removerObservador(EventosPartida.FINRONDA, jugadorServer);
    }

    public void onConfirmarVolverAJugar(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        // ver EventoObservado
        if (jugadorServer.salaActual == null)
            return; // mensaje invalido
        jugadorServer.salaActual.eventos.removerObservador(EventosPartida.FINPARTIDA, jugadorServer);
    }

    public void onCancelarVolverJugar(JugadorServer jugadorServer, MensajeNetwork mensaje) {
        // ver EventoObservado
        if (jugadorServer.salaActual == null)
            return; // mensaje invalido
        jugadorServer.salaActual.eventos.cancelarEvento(EventosPartida.FINPARTIDA);
    }

    public void onMensajeChat(JugadorServer jugadorServer, MensajeNetwork mensajeNetwork) {
        JsonObject objMensaje = mensajeNetwork.getMensaje();
        if (!JsonUtils.hasElement(objMensaje, "mensaje"))
            return; // mensaje invalido

        if (jugadorServer.salaActual == null) {
            return; // mensaje invalido
        }

        String mensaje = JsonUtils.getString(objMensaje, "mensaje");
        jugadorServer.salaActual.chat.nuevoMensaje(jugadorServer, mensaje);
    }

}
