class Title_Scene extends asd.Scene {
	boolean flag;

	protected void OnRegistered() {
		// 画像を読み込み、オブジェクトに設定する。
		asd.TextureObject2D obj = new asd.TextureObject2D();
		asd.Texture2D tex = asd.Engine.getGraphics().CreateTexture2D("res\\start.png");
		obj.setTexture(tex);

		// シーンにレイヤーを追加し、そのレイヤーにオブジェクトを追加する。
		asd.Layer2D layer = new asd.Layer2D();
		AddLayer(layer);
		layer.AddObject(obj);
	}

	protected void OnUpdated() {
		// マウスの左ボタンまたは Enter が押されるのを待つ。
		if ((asd.Engine.getMouse().getLeftButton().getButtonState() == asd.MouseButtonState.Push)
				|| (asd.Engine.getKeyboard().GetKeyState(asd.Keys.Enter) == asd.KeyState.Push)) {
			// フェードアウト・インによるシーン遷移を開始する。
			// 0.5秒かけてフェードアウトし、0.2秒かけてフェードイン。
			if (!flag) {
				asd.Engine.ChangeSceneWithTransition(new Game_Scene(), new asd.TransitionFade(0.5f, 0.2f));
				flag = true;
			}
		}
	}
}