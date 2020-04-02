package lingi2132;

public abstract class GlobalGenerator {
	protected String outputDir;
	
	public void generateClass() {}
	
	public GlobalGenerator(String outputDir) {
		this.outputDir = outputDir;
	}
	
	public static void main(String [] args) {
		
		GlobalGenerator gen = new Generator(args[0]);
		gen.generateClass();
		
	}
}