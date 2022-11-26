package main.java;

import java.util.Observer;

import main.java.controller.Controller;
import main.java.model.Map;
import main.java.view.IHM;

@SuppressWarnings("deprecation")
public class Main {
    public static void main(String[] args) {
        IHM view = new IHM();
        view.init();
        //model
        Map map=new Map();

        Controller c = new Controller(map,view.getField(),view.getLabel());
        view.getButton().addActionListener(c);

        map.addObserver((Observer) view.getLabel());
        map.addObserver((Observer) view.getLabel2());
        map.addObserver((Observer) view.getLabel3());
    }
}
