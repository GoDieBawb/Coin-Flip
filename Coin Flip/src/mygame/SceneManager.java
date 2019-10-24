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
import com.jme3.asset.TextureKey;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.texture.Texture;

/**
 *
 * @author Bob
 */
public class SceneManager extends AbstractAppState {
  
  public    SimpleApplication      app;
  public    AssetManager           assetManager;
  public    Node                   rootNode;
  public    float                  force;
  public    float                  maxHeight;
  public    float                  rotateSpeed;
  public    boolean                down;
  public    boolean                hitGround;
  public    boolean                inFlight;
  public    Coin                   currentCoin;
  public    int                    randomer;
  public    BitmapText             helloText;
    
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app);
    this.app           = (SimpleApplication) app;
    this.rootNode      = this.app.getRootNode();
    this.assetManager  = this.app.getAssetManager();
    down = false;
    hitGround = false;
    inFlight = false;
    force = 10;
    maxHeight = force*10;
    initCoin();
    initText();
    }
  
  public void initText(){
    app.getGuiNode().detachAllChildren();
    BitmapFont guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
    helloText = new BitmapText(guiFont, false);
    helloText.setSize(guiFont.getCharSet().getRenderedSize());
    helloText.setText("Swipe To Flip!");
    helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
    app.getGuiNode().attachChild(helloText);
    }
  
  public void initCoin(){
    Coin coin = new Coin();
    coin.model = new Node();

    Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    TextureKey key = new TextureKey("Models/Coin/rc-coin.jpg", true);
    Texture tex = assetManager.loadTexture(key);
    mat.setTexture("ColorMap", tex);

    Node hgeom = (Node) assetManager.loadModel("Models/Coin/Coin.j3o");
    hgeom.setMaterial(mat);
    hgeom.setName("Heads");
    hgeom.setLocalTranslation(0, -2, 0);
    coin.model.attachChild(hgeom);
    
    
    Node tgeom = (Node) assetManager.loadModel("Models/Coin/Coin.j3o");
    tgeom.setMaterial(mat);
    tgeom.setName("Tails");
    coin.model.attachChild(tgeom);
    tgeom.setLocalTranslation(0, -2, .2f);
    
    coin.attachChild(coin.model);
    rootNode.attachChild(coin);
    coin.rotateUpTo(new Vector3f(0, 0, 1));
    coin.setLocalTranslation(0, 2, 0);
    currentCoin = coin;
    System.out.println("Coin Initialized");
    }
  
  public void headsWin(){
    app.getStateManager().getState(CameraManager.class).chaseCam.setDefaultHorizontalRotation(-1.5f);
    app.getGuiNode().detachAllChildren();
    helloText.setText("Heads!");
    helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
    helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
    app.getGuiNode().attachChild(helloText);
    Node coin  = (Node) rootNode.getChild(0);
    coin.rotateUpTo(new Vector3f(0, 0, 1));
    coin.setLocalTranslation(0, 2, 0);
    }
  
  public void tailsWin(){
    app.getStateManager().getState(CameraManager.class).chaseCam.setDefaultHorizontalRotation(1.5f);
    app.getGuiNode().detachAllChildren();
    helloText.setText("Tails!");
    helloText.setLocalTranslation(300, helloText.getLineHeight(), 0);
    app.getGuiNode().attachChild(helloText);
    Node coin  = (Node) rootNode.getChild(0);
    coin.rotateUpTo(new Vector3f(0, 0, -1));
    coin.setLocalTranslation(0, 2, 0);
    }
  
    @Override
    public void update(float tpf) {
      Node coin  = (Node) rootNode.getChild(0);
      
        if(inFlight)
        if(!hitGround) {
        if (coin.getLocalTranslation().y < force*10 && !down){
          Vector3f moveDir = new Vector3f(0, force*3, 0);
          coin.setLocalTranslation(coin.getLocalTranslation().addLocal(moveDir.mult(tpf)));
          coin.rotate(10*tpf, 0, 0);
        
          } else {
          down = true;
          Vector3f moveDir = new Vector3f(0, -force*3, 0);
          coin.setLocalTranslation(coin.getLocalTranslation().addLocal(moveDir.mult(tpf)));
          coin.rotate(10*tpf, 0, 0); 
          }
      
        if (coin.getLocalTranslation().y < 10 && !hitGround){
          hitGround = true;
          down = false;
          inFlight = false;
          if (coin.getChild("Heads").getWorldTranslation().y < coin.getChild("Tails").getWorldTranslation().y)
          tailsWin();
          else
          headsWin();
          }
        }
      }
   }
