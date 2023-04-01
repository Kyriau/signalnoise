import java.util.LinkedList;

public class SignalNoise {
	
	private int dim;
	private LinkedList<Signal> signals;
	
	public SignalNoise(int dimension) {
		dim = dimension;
		signals = new LinkedList<>();
	}
	
	public void add(Signal signal) {
		
		if(signal.dimension() != dim)
			throw new IllegalArgumentException("Dimensional mismatch");
		
		signals.add(signal);
	
	}
	
	public int dimension() {
		return dim;
	}
	
	public double eval(double... coordinates) {
		
		if(coordinates.length != dim)
			throw new IllegalArgumentException("Dimensional mismatch");
		
		double sum = 0;
		for(Signal signal : signals) {
			sum += signal.eval(coordinates);
		}
		return sum;
		
	}
	
}