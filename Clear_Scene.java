class Clear_Scene extends asd.Scene
{
    protected void OnRegistered()
    {
        // 画像を読み込み、オブジェクトに設定する。
		asd.TextureObject2D obj = new asd.TextureObject2D();
		asd.Texture2D tex = asd.Engine.getGraphics().CreateTexture2D("res\\clear.png");
		obj.setTexture(tex);

		// シーンにレイヤーを追加し、そのレイヤーにオブジェクトを追加する。
		asd.Layer2D layer = new asd.Layer2D();
		AddLayer(layer);
		layer.AddObject(obj);
	}
	protected void OnUpdated() {
		// マウスの左ボタンが押されるのを待つ。
		if((asd.Engine.getMouse().getLeftButton().getButtonState() == asd.MouseButtonState.Push))
		{
			// フェードアウト・インによるシーン遷移を開始する。
			// 1秒かけてフェードアウトし、1.5秒かけてフェードイン。
			asd.Engine.ChangeSceneWithTransition(new Title_Scene(), new asd.TransitionFade(1.0f, 1.5f));
		}
    }
}