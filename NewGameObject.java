class NewGameObject extends IconObject {
    boolean flag;

    void OnClicked() {
        System.out.println("Icon: NewGame");
        if (flag == false) {
            asd.Engine.ChangeSceneWithTransition(new Game_Scene(), new asd.TransitionFade(1.0f, 1.5f));
            flag = true;
        }
    }
}