import java.util.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

class Clear_Scene extends asd.Scene {
	int score;
	int focus;
	boolean enter;
	asd.Layer2D layer;
	asd.TextureObject2D obj;
	AlphabetObject[] obj_alphabets;
	ArrayList<asd.Keys> keys;
	File file;
	FileWriter filewriter;
	// DecimalFormat deciscore;

	Clear_Scene(int s) {
		score = s;
	}

	protected void OnRegistered() {
		// レイヤーを作りシーンにレイヤーを追加する
		asd.Layer2D layer = new asd.Layer2D();
		AddLayer(layer);

		// 背景画像を読み込み、レイヤーにオブジェクトを追加する。
		asd.TextureObject2D obj = new asd.TextureObject2D();
		asd.Texture2D tex = asd.Engine.getGraphics().CreateTexture2D("res\\clear.png");
		obj.setTexture(tex);
		layer.AddObject(obj);

		// アルファベットの画像を読み込み、8個のオブジェクトに設定する
		obj_alphabets = new AlphabetObject[8];
		asd.Texture2D tex_alphabets = asd.Engine.getGraphics().CreateTexture2D("res\\alphabet_ed.png");

		// アルファベットの位置とテクスチャを初期化し、レイヤーにオブジェクトを追加する
		for (int i = 0; i < 8; i++) {
			obj_alphabets[i] = new AlphabetObject();
			obj_alphabets[i].setTexture(tex_alphabets); // 初期位置を空白で指定しておく
			obj_alphabets[i].init4clear(26, i);
			layer.AddObject(obj_alphabets[i]);
		}

		keys = new ArrayList<asd.Keys>();
		keys.add(asd.Keys.A);
		keys.add(asd.Keys.B);
		keys.add(asd.Keys.C);
		keys.add(asd.Keys.D);
		keys.add(asd.Keys.E);
		keys.add(asd.Keys.F);
		keys.add(asd.Keys.G);
		keys.add(asd.Keys.H);
		keys.add(asd.Keys.I);
		keys.add(asd.Keys.J);
		keys.add(asd.Keys.K);
		keys.add(asd.Keys.L);
		keys.add(asd.Keys.M);
		keys.add(asd.Keys.N);
		keys.add(asd.Keys.O);
		keys.add(asd.Keys.P);
		keys.add(asd.Keys.Q);
		keys.add(asd.Keys.R);
		keys.add(asd.Keys.S);
		keys.add(asd.Keys.T);
		keys.add(asd.Keys.U);
		keys.add(asd.Keys.V);
		keys.add(asd.Keys.W);
		keys.add(asd.Keys.X);
		keys.add(asd.Keys.Y);
		keys.add(asd.Keys.Z);
		keys.add(asd.Keys.Space);
		keys.add(asd.Keys.Backspace);
		keys.add(asd.Keys.Left);
		keys.add(asd.Keys.Right);
		keys.add(asd.Keys.Enter);

		// スコア記録用のファイルを (なければ) 作る
		try {
			file = new File("score.txt");
			file.createNewFile();
			filewriter = new FileWriter(file, true);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	protected void OnUpdated() {
		// キー入力を受け取る
		for (int i = 0; i < keys.size(); i++) {
			asd.KeyState state = asd.Engine.getKeyboard().GetKeyState(keys.get(i));
			if (state != asd.KeyState.Push)
				continue;

			// BackSpace
			if (i == 27) {
				if ((0 < focus && focus < obj_alphabets.length - 1) || (focus == obj_alphabets.length - 1
						&& obj_alphabets[obj_alphabets.length - 1].getAlphabetTexture() == 26)) {
					focus--;
				}
				obj_alphabets[focus].setAlphabetTexture(26);
				break;
			}

			// Left
			if (i == 28) {
				if (0 < focus)
					focus--;
				break;
			}

			// Right
			if (i == 29) {
				if (focus < obj_alphabets.length - 1)
					focus++;
				System.out.println("obj_alphabets.length = " + obj_alphabets.length);
				break;
			}

			// Enter
			if (i == 30) {
				// 外部ファイルに入力された文字列を保存
				System.out.println("score = " + score);
				try {
					filewriter.write(String.valueOf(score) + "\r\n");
					for (int j = 0; j < obj_alphabets.length; j++) {
						filewriter.write(obj_alphabets[j].toString());
						filewriter.write(",");
					}
					filewriter.write("\r\n");
					filewriter.close();
				} catch (IOException e) {
					System.out.println(e);
				}
				
				// 0.5秒かけてフェードアウトし、0.2秒かけてフェードイン。
				if (enter == false) {
					asd.Engine.ChangeSceneWithTransition(new Rank_Scene(), new asd.TransitionFade(0.5f, 0.2f));
					enter = true;
				}
				break;
			}

			// A-Z
			obj_alphabets[focus].setAlphabetTexture(i);
			if (focus < obj_alphabets.length - 1)
				focus++;
		}
	}
}