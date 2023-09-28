package com.example.cargo.Threads;

import com.example.cargo.Modelos.ModeloCrontabConfig;
import com.example.cargo.Modelos.ModeloFechaActual;
import com.example.cargo.Utilidades.ProcesosCrontabs;

public class ThreadCrontabs extends Thread {

    private ModeloCrontabConfig config;
    private Long timeNow;

    public ThreadCrontabs(ModeloCrontabConfig config, Long timeNow) {
        this.config = config;
        this.timeNow = timeNow;
    }

    public void run() {

        ProcesosCrontabs proceso = new ProcesosCrontabs();
        ModeloFechaActual fechaActual = proceso.ConsultaFechaActual();

        boolean validaMinuto = proceso.ValidaParteTiempo("minuto", config.getTiempo_minuto(), fechaActual);
        boolean validaHora = proceso.ValidaParteTiempo("hora", config.getTiempo_hora(), fechaActual);
        boolean validaDiaMes = proceso.ValidaParteTiempo("diames", config.getTiempo_diaMes(), fechaActual);
        boolean validaMes = proceso.ValidaParteTiempo("mes", config.getTiempo_mes(), fechaActual);
        boolean validaDiaSemana = proceso.ValidaParteTiempo("diasemana", config.getTiempo_diaSemana(), fechaActual);

        if (validaMinuto && validaHora && validaDiaMes && validaMes && validaDiaSemana) {
            System.out.println("Ejecutando - " + config.getConfig_nombre());
            proceso.ContactaURLHTTP(this.config, this.timeNow);
        }

    }

}
