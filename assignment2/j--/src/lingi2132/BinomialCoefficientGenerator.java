package lingi2132;

public abstract class BinomialCoefficientGenerator {
	protected String outputDir;
	
	public void generateClass() {}
	
	public BinomialCoefficientGenerator(String outputDir) {
		this.outputDir = outputDir;
	}
	
	public static void main(String[] args) {
		BinomialCoefficientGenerator gen = new Generator(args[0]);
		gen.generateClass();
		
	}
}
