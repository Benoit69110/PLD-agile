package ihm.windowMap;

import Model.MapInterface;
import controller.Controller;
import controller.state.FirstTourComputed;
import controller.state.MapLoaded;
import controller.state.RequestLoaded;
import controller.state.WaitOrder;
import ihm.windowMap.InputSection.InputMapWithDeliveryNPickupPoints;
import ihm.windowMap.InputSection.InputWindowLoadRequest;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class WindowMap extends Frame implements Observer //implements ActionListener, KeyListener
{
    private InputWindowLoadRequest inputPanel;

    private MapPanel mapPanel;
    private InputMapWithDeliveryNPickupPoints panelWithRequests;
    private Controller controller;


    public WindowMap(Controller controller)
    {
        super();
        this.controller=controller;
        inputPanel= new InputWindowLoadRequest(this, controller);
        inputPanel.setBackground(ColorPalette.inputPannel);
        this.add(inputPanel);

        mapPanel= new MapPanel();
        //mapPanel.setBounds((int)(0.05*Frame.height), (int)(0.05*Frame.height),(int)(0.9*Frame.height), (int)(0.9*Frame.height));
        this.add(mapPanel);
        this.setBackground(Color.BLACK);

        panelWithRequests= new InputMapWithDeliveryNPickupPoints(this, controller);

    }

    /**
     * update the panel with state of controller
     */
    public void updatePanel()
    {
       this.removeAllPanel();

       if(this.controller.getStateController() instanceof MapLoaded){
           this.add(inputPanel);
       }else if(
               this.controller.getStateController() instanceof RequestLoaded ||
                       this.controller.getStateController() instanceof WaitOrder ||
                       this.controller.getStateController() instanceof FirstTourComputed
       ){
           this.add(panelWithRequests);
       }

       this.add(mapPanel);
    }

    /**
     * remove all the panel of the window
     */
    private void removeAllPanel(){
        this.remove(mapPanel);
        this.remove(inputPanel);
        this.remove(panelWithRequests);
    }

    /**
     * ask the user if he want to continue tour (only in wait order state)
     */
    public void askToContinueTour(){
        if(controller.getStateController() instanceof WaitOrder) {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Timeout occurred, the tour may be not optimal. Would you continue to computing ?");
            if (result == 0)
                //System.out.println("You pressed Yes");
                controller.continueComputing();
            else if (result == 1)
                //System.out.println("You pressed NO");
                controller.stopComputing();
            else
                //System.out.println("You pressed Cancel");
                controller.back();
        }
    }


    @Override
    public void update(Observable o, Object arg)
    {
        if(o instanceof MapInterface && arg instanceof String){
            inputPanel.setErrorMsg((String)arg);
            this.revalidate();
            this.repaint();
        }

        else if(o instanceof MapInterface)
        {
            mapPanel.DisplayMap((MapInterface) o);
            panelWithRequests.updatePlanningRequestNotNull();
            this.revalidate();
            this.repaint();

        }

        this.updatePanel();
    }
}
