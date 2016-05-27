
public class ALUtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ALU alu = new ALU();
//		System.out.println(alu.integerSubtraction("10", "01111111", 8));
//		System.out.println(alu.integerDivision("1010", "0011", 4));
//		System.out.println(alu.oneAdder("1101"));
//		System.out.println(alu.signedAddition("1111", "1111", 4));
//		System.out.println(alu.floatTrueValue("1000000", 3, 3));
//		System.out.println(7/4);
		System.out.println(alu.floatAddition("00111111010000000000000000000000", "01000010111111101000000000000000", 8, 23, 6));
	}

}
