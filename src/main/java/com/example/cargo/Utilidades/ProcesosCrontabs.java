package com.example.cargo.Utilidades;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.example.cargo.Conexiones.ConexionMariaDB;
import com.example.cargo.Modelos.ModeloCrontabConfig;
import com.example.cargo.Modelos.ModeloFechaActual;

public class ProcesosCrontabs {

    public List<ModeloCrontabConfig> ConsultaCrontabs() {

        List<ModeloCrontabConfig> listaCrontabs = new ArrayList<>();

        String query = "CALL admin_cargo.sp_admin_cargo_crontabs_consultas(?,?)";

        try (Connection mariaDB = ConexionMariaDB.getConexion();
                CallableStatement cst = mariaDB.prepareCall(query);) {

            cst.setString(1, "Q");
            cst.setString(2, "QCCL");
            ResultSet rs = cst.executeQuery();

            while (rs.next()) {
                ModeloCrontabConfig resultado = new ModeloCrontabConfig();
                resultado.setConfig_nombre(rs.getString("acc_config_nombre"));
                resultado.setBandera_activo(rs.getString("acc_bandera_activo"));
                resultado.setMeta_descripcion(rs.getString("acc_meta_descripcion"));
                resultado.setTiempo_minuto(rs.getString("acc_tiempo_minuto"));
                resultado.setTiempo_hora(rs.getString("acc_tiempo_hora"));
                resultado.setTiempo_diaMes(rs.getString("acc_tiempo_diaMes"));
                resultado.setTiempo_mes(rs.getString("acc_tiempo_mes"));
                resultado.setTiempo_diaSemana(rs.getString("acc_tiempo_diaSemana"));
                resultado.setEjecucion_servidor(rs.getString("acc_ejecucion_servidor"));
                resultado.setEjecucion_puerto(rs.getString("acc_ejecucion_puerto"));
                resultado.setEjecucion_endpoint(rs.getString("acc_ejecucion_endpoint"));
                resultado.setEjecucion_proceso(rs.getString("acc_ejecucion_proceso"));
                resultado.setEjecucion_timeout(rs.getString("acc_ejecucion_timeout"));
                listaCrontabs.add(resultado);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la lista de crontabs: " + e);
        }

        return listaCrontabs;

    }

    public ModeloFechaActual ConsultaFechaActual() {
        ModeloFechaActual fechaActual = new ModeloFechaActual();

        SimpleDateFormat sdf = new SimpleDateFormat("MM;dd;HH;mm;EEEE");
        String fechaInicial = sdf.format(new Date());
        String[] arrayFecha = fechaInicial.split(";");

        fechaActual.setFechaMes(arrayFecha[0]);
        fechaActual.setFechaDia(arrayFecha[1]);
        fechaActual.setFechaHora(arrayFecha[2]);
        fechaActual.setFechaMinuto(arrayFecha[3]);

        switch (arrayFecha[4].toLowerCase()) {
            case "lunes":
                fechaActual.setFechaDiaSemana("1");
                break;
            case "martes":
                fechaActual.setFechaDiaSemana("2");
                break;
            case "miercoles":
                fechaActual.setFechaDiaSemana("3");
                break;
            case "jueves":
                fechaActual.setFechaDiaSemana("4");
                break;
            case "viernes":
                fechaActual.setFechaDiaSemana("5");
                break;
            case "sabado":
                fechaActual.setFechaDiaSemana("6");
                break;
            case "domingo":
                fechaActual.setFechaDiaSemana("0");
                break;
            case "monday":
                fechaActual.setFechaDiaSemana("1");
                break;
            case "tuesday":
                fechaActual.setFechaDiaSemana("2");
                break;
            case "wednesday":
                fechaActual.setFechaDiaSemana("3");
                break;
            case "thursday":
                fechaActual.setFechaDiaSemana("4");
                break;
            case "friday":
                fechaActual.setFechaDiaSemana("5");
                break;
            case "saturday":
                fechaActual.setFechaDiaSemana("6");
                break;
            case "sunday":
                fechaActual.setFechaDiaSemana("0");
                break;
        }

        return fechaActual;
    }

    public boolean ValidaParteTiempo(String tipoTiempo, String tiempo, ModeloFechaActual fechaActual) {

        int minutoActual = Integer.parseInt(fechaActual.getFechaMinuto());
        int horaActual = Integer.parseInt(fechaActual.getFechaHora());
        int diaMesActual = Integer.parseInt(fechaActual.getFechaDia());
        int mesActual = Integer.parseInt(fechaActual.getFechaMes());
        int diaSemanaActual = Integer.parseInt(fechaActual.getFechaDiaSemana());

        if (tiempo.equals("*")) {
            return true;
        } else {
            try {

                switch (tipoTiempo.toLowerCase()) {

                    case "minuto":
                        if (tiempo.contains(",") || tiempo.contains("-")) {
                            if (tiempo.contains(",")) {
                                String[] arregloMinutos = tiempo.split(",");
                                for (String minuto : arregloMinutos) {
                                    int minutoFinal = Integer.parseInt(minuto);
                                    if (minutoFinal == minutoActual) {
                                        return true;
                                    }
                                }
                            } else {
                                if (tiempo.contains("-")) {
                                    String[] arregloMinutos = tiempo.split("-");
                                    int minutoInicial = Integer.parseInt(arregloMinutos[0]);
                                    int minutoFinal = Integer.parseInt(arregloMinutos[1]);

                                    if (minutoInicial >= 0 && minutoFinal <= 59) {
                                        if (minutoActual >= minutoInicial && minutoActual <= minutoFinal) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else {
                            int minuto = Integer.parseInt(tiempo);
                            if (minuto >= 0 && minuto <= 59) {
                                if (minuto == minutoActual) {
                                    return true;
                                }
                            } else {
                                System.out.println("minuto fuera del rango correcto");
                            }
                        }
                        break;
                    case "hora":
                        if (tiempo.contains(",") || tiempo.contains("-")) {
                            if (tiempo.contains(",")) {
                                String[] arregloHoras = tiempo.split(",");
                                for (String hora : arregloHoras) {
                                    int horaFinal = Integer.parseInt(hora);
                                    if (horaFinal == horaActual) {
                                        return true;
                                    }
                                }
                            } else {
                                if (tiempo.contains("-")) {
                                    String[] arregloHoras = tiempo.split("-");
                                    int horaInicial = Integer.parseInt(arregloHoras[0]);
                                    int horaFinal = Integer.parseInt(arregloHoras[1]);

                                    if (horaInicial >= 0 && horaFinal <= 23) {
                                        if (horaActual >= horaInicial && horaActual <= horaFinal) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else {
                            int hora = Integer.parseInt(tiempo);
                            if (hora >= 0 && hora <= 23) {
                                if (hora == horaActual) {
                                    return true;
                                }
                            } else {
                                System.out.println("Hora fuera del rango correcto");
                            }
                        }
                        break;

                    case "diames":

                        if (tiempo.contains(",") || tiempo.contains("-")) {
                            if (tiempo.contains(",")) {
                                String[] arregloDiaMes = tiempo.split(",");
                                for (String diaMes : arregloDiaMes) {
                                    int diaMesFinal = Integer.parseInt(diaMes);
                                    if (diaMesFinal == minutoActual) {
                                        return true;
                                    }
                                }
                            } else {
                                if (tiempo.contains("-")) {
                                    String[] arregloDiaMes = tiempo.split("-");
                                    int diaMesInicial = Integer.parseInt(arregloDiaMes[0]);
                                    int diaMesFinal = Integer.parseInt(arregloDiaMes[1]);

                                    if (diaMesInicial >= 1 && diaMesFinal <= 31) {
                                        if (diaMesActual >= diaMesInicial && diaMesActual <= diaMesFinal) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else {
                            int diaMes = Integer.parseInt(tiempo);
                            if (diaMes >= 1 && diaMes <= 31) {
                                if (diaMes == diaMesActual) {
                                    return true;
                                }
                            } else {
                                System.out.println("dia mes fuera del rango correcto");
                            }
                        }

                        break;

                    case "mes":

                        if (tiempo.contains(",") || tiempo.contains("-")) {
                            if (tiempo.contains(",")) {
                                String[] arregloMes = tiempo.split(",");
                                for (String mes : arregloMes) {
                                    int mesFinal = Integer.parseInt(mes);
                                    if (mesFinal == mesActual) {
                                        return true;
                                    }
                                }
                            } else {
                                if (tiempo.contains("-")) {
                                    String[] arregloMes = tiempo.split("-");
                                    int mesInicial = Integer.parseInt(arregloMes[0]);
                                    int mesFinal = Integer.parseInt(arregloMes[1]);

                                    if (mesInicial >= 1 && mesFinal <= 12) {
                                        if (mesActual >= mesInicial && mesActual <= mesFinal) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else {
                            int mes = Integer.parseInt(tiempo);
                            if (mes >= 1 && mes <= 12) {
                                if (mes == mesActual) {
                                    return true;
                                }
                            } else {
                                System.out.println("mes fuera del rango correcto");
                            }
                        }
                        break;

                    case "diasemana":
                        if (tiempo.contains(",") || tiempo.contains("-")) {
                            if (tiempo.contains(",")) {
                                String[] arregloDiaSemana = tiempo.split(",");
                                for (String diaSemana : arregloDiaSemana) {
                                    int diaSemanaFinal = Integer.parseInt(diaSemana);
                                    if (diaSemanaFinal == diaSemanaActual) {
                                        return true;
                                    }
                                }
                            } else {
                                if (tiempo.contains("-")) {
                                    String[] arregloDiaSemana = tiempo.split("-");
                                    int diaSemanaInicial = Integer.parseInt(arregloDiaSemana[0]);
                                    int diaSemanaFinal = Integer.parseInt(arregloDiaSemana[1]);

                                    if (diaSemanaInicial >= 0 && diaSemanaFinal <= 6) {
                                        if (diaSemanaActual >= diaSemanaInicial && diaSemanaActual <= diaSemanaFinal) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        } else {
                            int diaSemana = Integer.parseInt(tiempo);
                            if (diaSemana >= 0 && diaSemana <= 6) {
                                if (diaSemana == diaSemanaActual) {
                                    return true;
                                }
                            } else {
                                System.out.println("dia semana fuera del rango correcto");
                            }
                        }

                        break;
                }

            } catch (Exception e) {
                System.out.println("Error al validar la fecha de ejecucion");
            }
        }

        return false;
    }

    public void ContactaURLHTTP(ModeloCrontabConfig crontab, Long timeNow) {
        Timestamp ts = new Timestamp(timeNow);
        Calendar cal = Calendar.getInstance();
        cal.setTime(ts);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");

        String fechaFinal = String.valueOf(sdf.format(cal.getTime()));
        String salida = "N/A";
        String url = "http://" + crontab.getEjecucion_servidor() + ":" + crontab.getEjecucion_puerto()
                + crontab.getEjecucion_endpoint() + "?proceso="
                + crontab.getEjecucion_proceso() + "&timeout=" + crontab.getEjecucion_timeout();

        // TODO: Insertar en la bitacora crontab cargo

        String nombreFinal = crontab.getConfig_nombre().trim().replaceAll(" ", "%20");

        String urlInicial = url + "&procesoNombre=" + nombreFinal + "&time=" + timeNow;

        System.out.println(urlInicial);
        return;

        /*
         * try {
         * 
         * URL urlFinal = new URL(urlInicial);
         * HttpURLConnection urlCon = (HttpURLConnection) urlFinal.openConnection();
         * urlCon.setConnectTimeout(Integer.parseInt(crontab.getEjecucion_timeout()) +
         * 5);
         * urlCon.setRequestMethod("GET");
         * urlCon.setRequestProperty("Content-Type", "application/json;");
         * urlCon.setRequestProperty("Connection", "keep-live");
         * urlCon.setRequestProperty("cache-control", "no-cache");
         * urlCon.setDoOutput(true);
         * 
         * // Respuesta
         * 
         * InputStream is = urlCon.getInputStream();
         * BufferedReader br = new BufferedReader(new InputStreamReader(is));
         * StringBuilder respuesta = new StringBuilder();
         * String linea;
         * while ((linea = br.readLine()) != null) {
         * respuesta.append(linea);
         * respuesta.append("\n");
         * }
         * br.close();
         * is.close();
         * urlCon.disconnect();
         * 
         * salida = respuesta.toString();
         * } catch (Exception e) {
         * System.out.println("Error al contactar el URL: " + e);
         * }
         * 
         */

        // System.out.println(salida);
        // TODO: Insertar en la bitacora crontab cargo

    }
}
