#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_texture;
uniform float u_pixelSizeX;
uniform float u_pixelSizeY;

varying vec4 v_color;
varying vec2 v_texCoords;

void main() {
  vec2 p = gl_FragCoord.xy;
  float value = 1.0;
  
  value += ((mod(p.x, u_pixelSizeX) + mod(p.y, u_pixelSizeY)) / 32.0);
  		
	gl_FragColor = v_color * value * texture2D(u_texture, v_texCoords);
}