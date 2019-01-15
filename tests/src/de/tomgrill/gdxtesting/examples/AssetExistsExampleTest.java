/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package de.tomgrill.gdxtesting.examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.zombies.Player;
import com.mygdx.zombies.items.PowerUp;
import de.tomgrill.gdxtesting.GameTest;
import org.junit.Test;

import com.badlogic.gdx.Gdx;

import org.mockito.Mockito;

//@RunWith(GdxTestRunner.class)
public class AssetExistsExampleTest extends GameTest {

	@Test
	public void backgroundFileExists() {
		assertTrue("This test will only pass when the badlogic.jpg file coming with a new project setup has not been deleted.",
				Gdx.files.internal("../core/assets/backround.jpg").exists());
	}
	@Test
	public void textureTest() {
		Texture texture = new Texture(512, 512, Pixmap.Format.RGB888);
		assertNotNull(texture);
	}
	@Test
	public void lightsTest() {
		Player player = Mockito.mock(Player.class);
		player.setHealth(3);
		assertEquals("Should output 3", 3, player.getHealth());
	}

}