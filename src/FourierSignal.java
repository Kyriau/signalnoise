public class FourierSignal implements Signal {
	
	private double coeff;
	private double[] freqs;
	
	public FourierSignal(double coefficient, double... frequencies) {
		
		if(frequencies == null || frequencies.length == 0)
			throw new IllegalArgumentException("Must be of at least dimension 1");
		
		coeff = coefficient;
		freqs = frequencies.clone();
		
	}
	
	public int dimension() {
		return freqs.length;
	}
	
	public double eval(double... coordinates) {
		
		if(coordinates.length != freqs.length)
			throw new IllegalArgumentException("Dimensional mismatch");
		
		double dot = 0;
		for(int i = 0; i < freqs.length; i++) {
			dot += freqs[i] * coordinates[i];
		}
		
		return coeff * Math.cos(dot);
		
	}
	
}