package controller;

import Model.Tour;
import Model.XML.MapFactory;
import Model.XML.MapInterface;
import ihm.windowMap.WelcomeWindow;
import ihm.windowMap.WindowMapLoadRequest;

public class Controller {
    private static MapInterface map;
    private static Tour tour;
    private static WelcomeWindow firstWindow;
    private static WindowMapLoadRequest window2;
    public static void main(String []args)
    {
        map= MapFactory.create();
        tour=map.getTour();
        firstWindow = new WelcomeWindow();
    }
    public static void  loadMap(String mapPath)
    {
       window2 = new WindowMapLoadRequest();
       map.addObserver(window2);
       map.loadMap(mapPath);
       firstWindow.dispose();
       map.notifyObservers();

    }
    public static void  loadRequest(String mapPath)
    {
        //load requests back method
        window2.changePanel();
    }


}