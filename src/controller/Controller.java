package controller;

import Model.Intersection;
import Model.MapFactory;
import Model.MapInterface;
import Model.Tour;
import controller.command.ListOfCommands;
import controller.state.InitialState;
import controller.state.StateController;
import ihm.windowMap.WelcomeWindow;
import ihm.windowMap.WindowMap;

/**
 * Class Controller
 */
public class Controller {
    private ListOfCommands listOfCommands;
    private StateController stateController;
    private MapInterface map;
    private Tour tour;
    private WelcomeWindow firstWindow;
    private WindowMap window2;

    /**
     * Constructeur
     */
    public Controller()
    {
        stateController = new InitialState();
        this.createMap();
        window2 = new WindowMap(this);
        window2.setVisible(false);
        firstWindow = new WelcomeWindow(this);
        map.addObserver(getFirstWindow());
        map.addObserver(getWindow2());
    }

    /**
     * change the controller state
     * @param state
     */
    //set state method
    protected void setCurrentState(StateController state){
        stateController = state;
    }

    //overrided method
    public void loadMap(String path){ this.stateController.loadMap(this, path);}

    public void loadRequest(String path){ this.stateController.loadRequest(this, path);}

    public void loadTour() { this.stateController.loadTour(this); }

    public void stopComputing() {this.stateController.stopComputing(this); }

    public void continueComputing() {this.stateController.continueTour(this, 10000); }

    @Deprecated
    public void addNewRequest(Intersection newPickup,
                              Intersection beforNewPickup,
                              int pickupDuration,
                              Intersection newDelivery,
                              Intersection beforNewDelivery,
                              int deliveryDuration) {
        this.stateController.addNewRequest(this, listOfCommands, newPickup, beforNewPickup,
                pickupDuration, newDelivery, beforNewDelivery, deliveryDuration);
    }

    public void chooseNewPickup(Intersection theNewPickup, int pickupDuration){ this.stateController.chooseNewPickup(this, theNewPickup, pickupDuration );}

    public void chooseBeforNewPickup(Intersection theBeforNewPickup){this.stateController.chooseBeforNewPickup(this, theBeforNewPickup);}

    public void chooseNewDelivery(Intersection theNewDelivery, int deliveryDuration){this.stateController.chooseNewDelivery(this, theNewDelivery, deliveryDuration);}

    public void chooseBeforNewDelivery(Intersection theBeforNewDelivery){this.stateController.chooseBeforNewDelivery(this, theBeforNewDelivery, listOfCommands);};

    public void deleteRequest(Intersection intersectioToDelete){this.stateController.deleteRequest(this, intersectioToDelete, listOfCommands);}

    public void back() {this.stateController.back(this);}

    public void redo(){this.stateController.redo(listOfCommands);}

    public void undo(){this.stateController.undo(listOfCommands);}

    //--------------- getter ---------------

    public StateController getStateController() {
        return stateController;
    }

    public MapInterface getMap() {
        return map;
    }

    public Tour getTour() {
        return tour;
    }

    public WelcomeWindow getFirstWindow() {
        return firstWindow;
    }

    public WindowMap getWindow2() {
        return window2;
    }

    //--------------- setter ---------------
    public void setStateController(StateController stateController) {
        this.stateController = stateController;
    }

    public void setMap(MapInterface map) {
        this.map = map;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public void createMap()
    {
        map = MapFactory.create();
    }

    //--------------- main ---------------
    public static void main(String []args)
    {
        new Controller();
    }

}
