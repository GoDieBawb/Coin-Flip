/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import java.util.Random;

/**
 *
 * @author Bob
 */
public class InteractionManager extends AbstractAppState implements ActionListener {

  public    SimpleApplication      app;
  public    AssetManager           assetManager;
  public    InputManager           inputManager;
  public    AppStateManager        stateManager;
  public    Node                   rootNode;
  public    Node                   coin;
  public    boolean                click;
  public    boolean                forcing;
  public    int                    clickDelay;
  public    Vector2f               firstPos;
  public    Vector2f               secondPos;
  public    SceneManager           scene;
    
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app);
    this.app           = (SimpleApplication) app;
    this.rootNode      = this.app.getRootNode();
    this.inputManager  = this.app.getInputManager();
    this.assetManager  = this.app.getAssetManager();
    this.stateManager  = this.app.getStateManager();
    scene = stateManager.getState(SceneManager.class); 
    coin = (Node) rootNode.getChild(0);
    clickDelay = 0;
    forcing = false;
    initKeys();
    }
  
  public void initKeys() {
    inputManager.addMapping("Click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
    inputManager.addListener(this, "Click");
    }
  
  public void onAction(String binding, boolean isPressed, float tpf) {
    if (binding.equals("Click"))
    click = isPressed;
    }
  
  @Override
  public void update(float tpf){
    
    if (click) {
      
      if (clickDelay > 5 && !scene.inFlight)  {
        
        app.getGuiNode().detachAllChildren();
        scene.helloText.setText("Good Luck!");
        scene.helloText.setLocalTranslation(300, scene.helloText.getLineHeight(), 0);
        
        Random rand = new Random();
        int mult = rand.nextInt(2) + 1;      
        app.getGuiNode().attachChild(scene.helloText);
        scene.force = 10 * mult;
        scene.currentCoin.setLocalTranslation(0, 25, 0);
        scene.inFlight = true;
        scene.hitGround = false;
        
        } else if (clickDelay == 0 && !forcing) {
        firstPos = inputManager.getCursorPosition();
        forcing = true;
        
        } else {
        clickDelay++;
        }
      
      } else {
      forcing = false;
      clickDelay = 0;
      }
    }
  }
