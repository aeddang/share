precision mediump float;
	        
uniform sampler2D uTexture;


uniform vec3 uLightPos;		


        		
varying vec2 vTextureCoord;
varying vec3 vPosition;
varying vec4 vColor;
varying vec3 vNormal;





void main() {
	        
				float distance = length(uLightPos - vPosition);
	            vec3 lightVector = normalize(uLightPos - vPosition);
	            
	            
	           
	            
	            float diffuse;
	            if (gl_FrontFacing) {
	            						diffuse = max(dot(vNormal, lightVector), 0.0);
	            					} else 
	            					{
	            						diffuse = max(dot(-vNormal, lightVector), 0.0);
	            					}
	            diffuse = diffuse * (1.0 / (1.0 + (0.10 * distance)));
	            diffuse = diffuse + 0.3;
	           
	            
	            gl_FragColor = vColor * diffuse * texture2D(uTexture, vTextureCoord);
	            
}