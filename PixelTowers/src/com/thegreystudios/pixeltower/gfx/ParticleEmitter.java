package com.thegreystudios.pixeltower.gfx;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class ParticleEmitter {
	public static final int DUST = 1;
	public static final int EXPLOSION = 2;
	public static final int BLOOD = 3;
	public static final int HIGHSCORE = 4;
	public Array<Particle> particles = new Array<Particle>();
	Array<Particle> inactives = new Array<Particle>();
	public ParticleEmitterDescription desc;
	public boolean active;
	public int type;
	public int x;
	public int y;

	public ParticleEmitter(ParticleEmitterDescription desc, int type) {
		this.desc = desc;
		this.type = type;

		this.x = (this.y = 0);

		prepareParticles();
	}

	private void prepareParticles() {
		for (int i = 0; i < this.desc.particleAmount; i++) {
			Particle p = this.desc.emitParticle();
			this.inactives.add(p);
		}
	}

	public void update(float deltaTime) {
		if (!this.active) {
			return;
		}
		if (this.particles.size < 0) {
			this.active = false;
		}
		for (int i = 0; i < this.particles.size; i++) {
			Particle p = (Particle) this.particles.get(i);
			if (p.active) {
				((Particle) this.particles.get(i)).update(deltaTime);
			} else {
				this.particles.removeIndex(i);
				this.inactives.add(p);
				i--;
			}
		}
	}

	public void start() {
		this.active = true;

		for (int i = 0; i < this.inactives.size; i++) {
			Particle p = (Particle) this.inactives.get(i);

			p.reset();
			this.particles.add(p);

			this.inactives.removeIndex(i);
			i--;
		}
	}

	public static class Particle {
		private float stateTime;
		public Vector2 startingPosition = new Vector2();
		public Vector2 startingVelocity = new Vector2();
		public Vector2 startingAcceleration = new Vector2();

		public Vector2 position = new Vector2();
		public Vector2 velocity = new Vector2();
		public Vector2 acceleration = new Vector2();
		public float lifeTime;
		public Color color = new Color(Color.WHITE);
		public boolean active;

		public void update(float deltaTime) {
			this.stateTime += deltaTime;

			if (this.active) {
				this.velocity.x += this.acceleration.x * deltaTime;
				this.velocity.y += this.acceleration.y * deltaTime;

				this.position.x += this.velocity.x * deltaTime;
				this.position.y += this.velocity.y * deltaTime;

				if (this.stateTime > this.lifeTime)
					this.active = false;
			}
		}

		public void reset() {
			this.position.set(this.startingPosition);
			this.velocity.set(this.startingVelocity);
			this.acceleration.set(this.startingAcceleration);

			this.active = true;
			this.stateTime = 0.0F;
		}
	}
}