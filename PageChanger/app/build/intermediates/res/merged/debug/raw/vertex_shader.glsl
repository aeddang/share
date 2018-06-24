uniform mat4 uMVPMatrix;
uniform mat4 uMVMatrix;



attribute vec4 aPosition;
attribute vec2 aTextureCoord;
attribute vec4 aColor;
attribute vec3 aNormal;

varying vec2 vTextureCoord;
varying vec3 vPosition;
varying vec4 vColor;
varying vec3 vNormal;



void main() {
	         	
	         	vPosition = vec3(uMVMatrix * aPosition);        
	         	vColor = aColor;
	         	vTextureCoord = aTextureCoord;
	         	vNormal = normalize(vec3(uMVMatrix * vec4(aNormal, 0.0)));
				
				gl_Position = uMVPMatrix * aPosition;
	        
}