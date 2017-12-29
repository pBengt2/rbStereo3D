import javax.swing.JOptionPane;


//Peter Bengtson
//pBengt2
//Monday 3pm
//Combine two images to create a 3d image

//video
//https://youtu.be/ONGKC8TPWhY

public class Project2pBengt2
{
	public static void main (String[] args) 
	{
		Object stringArray[] = {"Picture", "Video"};

		int z = 2;
		z = JOptionPane.showOptionDialog(null, "Create a single 3d picture or a batch of images for a video", "3d Conversion",
		JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, stringArray[0]);
		
		if (z==0){ //image
			//create left picture
			String filenameLeft;
			filenameLeft = FileChooser.pickAFile();
			
			Picture pLeft;
			pLeft = new Picture(filenameLeft);
			
			//create right picture
			String filenameRight;
			filenameRight = FileChooser.pickAFile();
			Picture pRight;
			pRight = new Picture(filenameRight);
			
			//check if images are the same dimensions
			if (pLeft.getHeight()!=pRight.getHeight() || pLeft.getWidth()!=pRight.getWidth()){
				SimpleOutput.showError("Error: The images need to have the same dimensions.");
				System.exit(1); 
			}
			else{
				//use both pictures to make a 3d image
				Picture p = make3dImage(pLeft, pRight);
				//display the picture
				p.show();
			
				//save the image
				String filename;
				filename = FileChooser.pickAFile();
				if (filename!=null)
					p.write(filename);
			}
		}	
		
		else if (z==1){ //video
			JOptionPane.showMessageDialog(null, "Click 'OK' to start");
			//TODO: insert FileChooser for inital left and right file
			//TODO: prompt for number of frames
			make3dVideo("l", "r", 120);
		}		
		else{ //neither option selected 
				System.exit(0); //exit the program				
		}
		
		
		System.out.println("");
		System.out.println("End Java Exection");
		System.exit(0);
	}//end of main	  

	public static Picture make3dImage (Picture pL, Picture pR){			
		Pixel pixL, pixR, pix;		
		  
		int width = pL.getWidth();
		int height = pL.getHeight();
		
		Picture p = new Picture(width,height); 
		
		//initialize red, green, and blue values
		int rVal, gVal, bVal;
		int weighted;
		
		for(int x=0; x < width; x++){
			for (int y=0; y < height; y++){
				//get the pixel of same location on each picture
				pixL = pL.getPixel(x,y);
				pixR = pR.getPixel(x,y);
				pix = p.getPixel(x,y);
				
				//find rgb values of left image
				rVal = pixL.getRed();
				gVal = pixL.getGreen();
				bVal = pixL.getBlue();
				
				//find weighted average
				weighted =(int)(rVal*0.29+gVal*.59+bVal*0.12);
				
				pix.setRed(weighted);
				
				//find rgb values of right image
				rVal = pixR.getRed();
				gVal = pixR.getGreen();
				bVal = pixR.getBlue();			
				
				weighted =(int)(rVal*0.29+gVal*.59+bVal*0.12);	
				
				pix.setGreen(weighted); 
				pix.setBlue(weighted);
			}
		}
		return p;
	}//end of make3dImage
	

	public static void make3dVideo (String fileL, String fileR, int numFrames){
		fileL = "D:\\3dImages\\l"; //TODO: replace with fileL -".jpg"
		fileR = "D:\\3dImages\\r"; //TODO: replace with fileR -".jpg"
		
		Picture pictL, pictR, pict;
		pictL = new Picture(fileL+".jpg");
		pictR = new Picture(fileR+".jpg");
		pict = new Picture(pictL.getWidth(), pictL.getHeight());
		
		int p1=0;//percent
		int p2=0;
		for (int i=1; i<numFrames;i++){
			pictL = new Picture(fileL+i+".jpg");
			pictR = new Picture(fileR+i+".jpg");
			pict = make3dImage(pictL, pictR);
			
			pict.write("D:\\3dImages\\output\\"+i+".jpg"); //TODO: replace with FileChooser for output location"
			
			p2 = (int) 100*i/numFrames;
			if (p2>p1)
				System.out.println(p2 +"%");
			p1=p2;
		}
		System.out.println("Completed");
	}
}