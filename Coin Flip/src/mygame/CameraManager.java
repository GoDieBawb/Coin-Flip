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
import com.jme3.input.ChaseCamera;
import com.jme3.input.InputManager;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class CameraManager extends AbstractAppState {
 
  public    SimpleApplication      app;
  public    AssetManager           assetManager;
  public    Node                   rootNode;
  public    Node                   coin;
  public    ChaseCamera            chaseCam;
  public    InputManager           inputManager;    
    
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app);
    this.app           = (SimpleApplication) app;
    this.rootNode      = this.app.getRootNode();
    this.assetManager  = this.app.getAssetManager();
    this.inputManager  = this.app.getInputManager();
    coin = (Node) rootNode.getChild(0);
    initCamera();
    }
  
  public void initCamera(){
    app.getFlyByCamera().setEnabled(false);
    chaseCam = new ChaseCamera(app.getCamera(), coin, inputManager);
    chaseCam.setDefaultDistance(15);
    chaseCam.setDragToRotate(false);
    chaseCam.setDefaultHorizontalRotation(-1.5f);
    chaseCam.setDefaultVerticalRotation(-5);
    chaseCam.setMinVerticalRotation(-5);
    chaseCam.setMaxVerticalRotation(-5);
    chaseCam.setUpVector(new Vector3f(0f, 1f, 0f));
    chaseCam.setEnabled(true);
    }
  }
