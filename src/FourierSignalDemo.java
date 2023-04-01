import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class FourierSignalDemo extends JPanel {
	
	private static int SIZE = 800;
	private static int FREQS = 7;
	private static int ANGS = 11;
	
	private SignalNoise noise;
	private BufferedImage image;
	
	private Random rand = new Random(0x1234);
	
	public FourierSignalDemo() {
		
		long t0 = System.currentTimeMillis();
		
		noise = new SignalNoise(2);
		double delta = Math.PI / ANGS;
		for(int f = -FREQS; f <= FREQS; f++) {
			for(double theta = 0; Math.PI - theta >= 0.0001; theta += delta) {
				double xfreq = f * Math.cos(theta);
				double yfreq = f * Math.sin(theta);
				double coeff = 2 * rand.nextDouble() - 1;
				noise.add(new FourierSignal(coeff, xfreq, yfreq));
			}
		}
		
		double[][] raw = new double[SIZE][SIZE];
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		for(int x = 0; x < SIZE; x++) {
			for(int y = 0; y < SIZE; y++) {
				double scale = 2 * Math.PI / SIZE;
				raw[x][y] = noise.eval(scale * x, scale * y);
				min = Math.min(min, raw[x][y]);
				max = Math.max(max, raw[x][y]);
			}
		}
		
		image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x < SIZE; x++) {
			for(int y = 0; y < SIZE; y++) {
				int intensity = (int) (255 * (raw[x][y] - min) / (max - min));
				int rgb = intensity | (intensity << 8) | (intensity << 16);
				image.setRGB(x, y, rgb);
			}
		}
		
		long t1 = System.currentTimeMillis();
		System.out.println("Time: " + (t1 - t0));
		
		try {
			ImageIO.write(image, "png", new File(String.format("output/f%da%dfourier.png", FREQS, ANGS)));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		g.drawImage(image, 0, 0, null);
		
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(SIZE, SIZE);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		FourierSignalDemo demo = new FourierSignalDemo();
		frame.add(demo);
		
		frame.setVisible(true);
	}
	
}