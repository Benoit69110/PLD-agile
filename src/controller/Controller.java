package controller;

import Model.MapFactory;
import Model.MapInterface;
import Model.PlanningRequest;
import Model.Tour;
import ihm.windowMap.WelcomeWindow;
import ihm.windowMap.WindowMap;

public class Controller {
    private static MapInterface map;
    private static Tour tour;
    private static WelcomeWindow firstWindow;
    private static WindowMap window2;
    private static PlanningRequest planningRequest;
    public static void main(String []args)
    {
        map= MapFactory.create();
        tour=map.getTour();
        firstWindow = new WelcomeWindow();
        map.addObserver(firstWindow);
    }
    public static void  loadMap(String mapPath)
    {
        try {
            map.loadMap(mapPath);
            window2 = new WindowMap();
            map.addObserver(window2);
            firstWindow.dispose();
            map.notifyObservers();
        }
        catch(Exception e)
        {
           // e.printStackTrace();

        }

    }


    public static void  loadRequest(String mapPath)
    {
        try{
            map.loadRequest(mapPath);
            //load requests back method
            window2.changePanel(0);
            map.notifyObservers();
            //System.out.println(map.getPlanningRequest().getRequestList());

        }catch(Exception e)
        {
           // e.printStackTrace();

        }
    }

    public static PlanningRequest getPlanningRequest()
    {
        System.out.println(map.getPlanningRequest().getRequestList());
        map.getPlanningRequest().getStartingPoint();
        return map.getPlanningRequest();
    }

    public static void loadTour()
    {
        map.computeTour(300);
        System.out.println("tour loaded");
    }

    public static void backToWelcomeWindow()
    {
        window2.dispose();
        firstWindow= new WelcomeWindow();
        map= MapFactory.create();
    }
    public static void backToWindowLoadRequest()
    {
        window2.changePanel(1);
        map.resetPlanningRequest();
    }


}
