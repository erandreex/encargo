package com.example.cargo.Controladores;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.cargo.Modelos.ModeloCrontabConfig;
import com.example.cargo.Threads.ThreadCrontabs;
import com.example.cargo.Utilidades.ProcesosCrontabs;

@Component
public class ControladorCrontabs extends Thread {

    public void run() {
        int minutoAnterior = 100;

        while (true) {

            try {
                Long timeNow = Calendar.getInstance().getTimeInMillis();
                Timestamp ts = new Timestamp(timeNow);
                Calendar cal = Calendar.getInstance();
                cal.setTime(ts);

                SimpleDateFormat sdfSegundo = new SimpleDateFormat("ss");
                SimpleDateFormat sdfMinuto = new SimpleDateFormat("mm");

                int segundo = 10;
                int minutoActual = minutoAnterior;

                try {
                    segundo = Integer.parseInt(sdfSegundo.format(cal.getTime()));
                    minutoActual = Integer.parseInt(sdfMinuto.format(cal.getTime()));

                } catch (Exception e) {
                    System.out.println("Error al transformar segundos y minutos: " + e);
                }

                if (segundo >= 0 && segundo <= 5 && minutoActual != minutoAnterior) {
                    minutoAnterior = minutoActual;
                    List<ModeloCrontabConfig> listaCrontabs = new ProcesosCrontabs().ConsultaCrontabs();
                    listaCrontabs.forEach(element -> {
                        new ThreadCrontabs(element, timeNow).start();
                    });
                }

                Thread.sleep(5000);

            } catch (Exception e) {
                System.out.println("Error al recorrer los crontabs por ejecutar: " + e);
            }

        }
    }
}
