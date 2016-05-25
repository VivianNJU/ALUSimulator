/**
 * 模拟ALU进行整数和浮点数的四则运算
 * @151250160_吴静琦[请将此处修改为“学号_姓名”]
 *
 */

public class ALU {

	/**
	 * 生成十进制整数的二进制补码表示。<br/>
	 * 例：integerRepresentation("9", 8)
	 * @param number 十进制整数。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制补码表示的长度
	 * @return number的二进制补码表示，长度为length
	 */
	public String integerRepresentation (String number, int length) {
		// TODO YOUR CODE HERE.
		String result = "";
		int num = Integer.parseInt(number);
		if(num<0){
			int i = (int) (Math.pow(2,length-1)+num);
			result = "1"+integerRepresentation(Integer.toString(i),length-1);
		}else{
			for(int i = 0;i<length;i++){
				if(num%2==1){
					result = "1"+result;
				}else{
					result = "0"+result;
				}
				
				num = num/2;
			}
		}
		return result;
	}
	
	/**
	 * 生成十进制浮点数的二进制表示。
	 * 需要考虑 0、反规格化、正负无穷（“+Inf”和“-Inf”）、 NaN等因素，具体借鉴 IEEE 754。
	 * 舍入策略为向0舍入。<br/>
	 * 例：floatRepresentation("11.375", 8, 11)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return number的二进制表示，长度为 1+eLength+sLength。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String floatRepresentation (String number, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		Double num = Double.parseDouble(number);
		int half = (int)(Math.pow(2, eLength-1)-1);
		
		int integ = (int)Math.abs(num);
		Double doub = Math.abs(num)-integ;//全部为正
		//比较是否超出可以表示的最大值
		if(Math.abs(num)>((2-Math.pow(2, -sLength))*Math.pow(2, half))){
			String eTemp = "";
			String sTemp = "";
			
			for(int i=0;i<eLength;i++){
				eTemp += "1";
			}
			
			for(int j=0;j<sLength;j++){
				sTemp += "0";
			}
			if(num>0)
				return "0"+eTemp+sTemp;
			else
				return "1"+eTemp+sTemp;
		}//零的表示方法
		else if(Math.abs(num)<Math.pow(2, 1-half)){
			String temp;
			if(number.trim().charAt(0) == '-')
				temp = "1";
			else
				temp = "0";
			for(int i=0;i<sLength+eLength;i++)
				temp += "0";
			return temp;
		}
		
		String symbol;//symbol是符号位。0代表非负数，1代表负数
		if(num>=0)
			symbol = "0";
		else
			symbol = "1";
		
		String integStr = "";//算出整数部分的二进制表示
		while(integ!=0){
			if(integ%2==1){
				integStr = "1"+integStr;
			}else{
				integStr = "0"+integStr;
			}
			
			integ = integ/2;
		}
		integ = (int)Math.abs(num);//重新初始化
		
		String doubStr = "";
		for(int i = 0;i<sLength+half-1;i++){
			doub = doub*2;
			if(doub<1){
				doubStr = doubStr+"0";
			}else{
				doubStr = doubStr+"1";
				doub = doub-1;
			}
		}
		doub = Math.abs(num)-integ;//重新初始化
		
		//反规则化数的表示
		if(Math.abs(num)<Math.pow(2, -half+1)){
			String eTemp = "";
			
			for(int i=0;i<eLength;i++){
				eTemp += "0";
			}
			String sTemp = doubStr.substring(half-1);
			
			return symbol+eTemp+sTemp;
		}
		//整数部分二进制表示的长度减1加上half就是指数部分表示的数
		if(integ>0){
			String eTemp = integerRepresentation(Integer.toString(integStr.length()-1+half), eLength);
			
			String sTemp = integStr.substring(1)+doubStr;
			
			sTemp = sTemp.substring(0, sLength);
			
			return symbol+eTemp+sTemp;
		}
		//小数部分的1，左移
		if(integ==0){
			int exponent = half - 1 - doubStr.indexOf("1");
			String eTemp = integerRepresentation(Integer.toString(exponent),eLength);
			String sTemp = doubStr.substring(doubStr.indexOf("1")+1);
			if(sTemp.length()<sLength){
				for(int i =0;i<sLength-doubStr.substring(doubStr.indexOf("1")+1).length();i++){
					sTemp+="0";
				}
			}else{
				sTemp = sTemp.substring(0,sLength);
			}

			return symbol+eTemp+sTemp;
		}
		
		return null;
	}
	
	/**
	 * 生成十进制浮点数的IEEE 754表示，要求调用{@link #floatRepresentation(String, int, int) floatRepresentation}实现。<br/>
	 * 例：ieee754("11.375", 32)
	 * @param number 十进制浮点数，包含小数点。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 * @param length 二进制表示的长度，为32或64
	 * @return number的IEEE 754表示，长度为length。从左向右，依次为符号、指数（移码表示）、尾数（首位隐藏）
	 */
	public String ieee754 (String number, int length) {
		// TODO YOUR CODE HERE.
		if(length==32){
			return floatRepresentation(number,8,23);
		}else
		if(length==64){
			return floatRepresentation(number,11,52);
		}
		return "Wrong!";
	}
	
	/**
	 * 计算二进制补码表示的整数的真值。<br/>
	 * 例：integerTrueValue("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位
	 */
	public String integerTrueValue (String operand) {
		// TODO YOUR CODE HERE.
		char[] numChar = operand.toCharArray();
		int num = 0;
		for(int i = 0;i<operand.length()-1;i++){
			if(numChar[operand.length()-1-i]=='1'){
				num = num+(int)(Math.pow(2, i));
			}
		}
		if(numChar[0]=='1'){
			num = num-(int) (Math.pow(2, operand.length()-1));
		}
		return Integer.toString(num);
	}
	
	/**
	 * 计算二进制原码表示的浮点数的真值。<br/>
	 * 例：floatTrueValue("01000001001101100000", 8, 11)
	 * @param operand 二进制表示的操作数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return operand的真值。若为负数；则第一位为“-”；若为正数或 0，则无符号位。正负无穷分别表示为“+Inf”和“-Inf”， NaN表示为“NaN”
	 */
	public String floatTrueValue (String operand, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		
		return null;
	}
	
	/**
	 * 按位取反操作。<br/>
	 * 例：negation("00001001")
	 * @param operand 二进制表示的操作数
	 * @return operand按位取反的结果
	 */
	public String negation (String operand) {
		// TODO YOUR CODE HERE.
		String result = "";
		for(int i = 0;i<operand.length();i++){
			int a = 1-operand.charAt(i)+'0';
			result = result+Integer.toString(a);
		}
		return result;
	}
	
	/**
	 * 左移操作。<br/>
	 * 例：leftShift("00001001", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 左移的位数
	 * @return operand左移n位的结果
	 */
	public String leftShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		String result = "";
		for(int i = operand.length()-1;i>n-1;i--){
			result = operand.charAt(i)+result;
		}
		for(int i = 0;i<n;i++){
			result = result+"0";
		}
		return result;
	}
	
	/**
	 * 逻辑右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand逻辑右移n位的结果
	 */
	public String logRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		String result = "";
		for(int i = operand.length()-n-1;i>=0;i--){
			result = operand.charAt(i)+result;
		}
		for(int i = 0;i<n;i++){
			result = "0"+result;
		}
		return result;
	}
	
	/**
	 * 算术右移操作。<br/>
	 * 例：logRightShift("11110110", 2)
	 * @param operand 二进制表示的操作数
	 * @param n 右移的位数
	 * @return operand算术右移n位的结果
	 */
	public String ariRightShift (String operand, int n) {
		// TODO YOUR CODE HERE.
		String result = "";
		for(int i = operand.length()-n-1;i>=0;i--){
			result = operand.charAt(i)+result;
		}
		for(int i = 0;i<n;i++){
			result = operand.charAt(0)+result;
		}
		return result;
	}
	
	/**
	 * 全加器，对两位以及进位进行加法运算。<br/>
	 * 例：fullAdder('1', '1', '0')
	 * @param x 被加数的某一位，取0或1
	 * @param y 加数的某一位，取0或1
	 * @param c 低位对当前位的进位，取0或1
	 * @return 相加的结果，用长度为2的字符串表示，第1位表示进位，第2位表示和
	 */
	public String fullAdder (char x, char y, char c) {
		// TODO YOUR CODE HERE.
		String result = "";
		//sum
		result = Integer.toString((x-'0')^(y-'0')^(c-'0'));
		//carry
		result = Integer.toString(((x-'0')&(y-'0'))|((c-'0')&(y-'0'))|((x-'0')&(c-'0')))+result;
		return result;
	}
	
	/**
	 * 4位先行进位加法器。<br/>
	 * 例：claAdder("1001", "0001", '1')
	 * @param operand1 4位二进制表示的被加数
	 * @param operand2 4位二进制表示的加数
	 * @param c 低位对当前位的进位，取0或1
	 * @return 长度为5的字符串表示的计算结果，其中第1位是最高位进位，后4位是相加结果，其中进位不可以由循环获得
	 */
	public String claAdder (String operand1, String operand2, char c) {
		// TODO YOUR CODE HERE.
		String result = "";
		int[] P = new int[4];
		int[] G = new int[4];
		int[] C = new int[5];
		for(int i =0;i<operand1.length();i++){
			//算出Pi
			P[i] = (operand1.charAt(operand1.length()-1-i)-'0')|(operand2.charAt(operand1.length()-1-i)-'0');
			//算出Gi
			G[i] = (operand1.charAt(operand1.length()-1-i)-'0')&(operand2.charAt(operand1.length()-1-i)-'0');
		}
		
		C[0] = (int)(c - '0');
		C[1] = G[0] 
				|(P[0] & C[0]);
		C[2] = G[1] 
				|(P[1] & G[0]) 
				|(P[1] & P[0] & C[0]);
		C[3] = G[2] 
				|(P[2] & G[1]) 
				|(P[2] & P[1] & G[0]) 
				|(P[2] & P[1] & P[0] & C[0]);
		C[4] = G[3] 
				|(P[3] & G[2]) 
				|(P[3] & P[2] & G[1]) 
				|(P[3] & P[2] & P[1] & G[0]) 
				|(P[3] & P[2] & P[1] & P[0] & C[0]);
		
		for(int i =0;i<operand1.length();i++){
			result = Integer.toString((operand1.charAt(operand1.length()-1-i)-'0')^(operand2.charAt(operand1.length()-1-i)-'0')^C[i])+result;
		}
		result = Integer.toString(C[4])+result;
		return result;
	}
	
	/**
	 * 加一器，实现操作数加1的运算。
	 * 需要模拟{@link #fullAdder(char, char, char) fullAdder}来实现，但不可以调用{@link #fullAdder(char, char, char) fullAdder}。<br/>
	 * 例：oneAdder("00001001")
	 * @param operand 二进制补码表示的操作数
	 * @return operand加1的结果，长度为operand的长度加1，其中第1位指示是否溢出（溢出为1，否则为0），其余位为相加结果
	 */
	public String oneAdder (String operand) {
		// TODO YOUR CODE HERE.
		String result = "";
		int[] c = new int[operand.length()+1];
		c[0] = 1;
		for(int i = 0;i<operand.length();i++){
			result = (char)((operand.charAt(operand.length()-1-i)-'0')^c[i]+'0')+result;
			c[i+1] = (operand.charAt(operand.length()-1-i)-'0')&c[i];
		}
		char overflow = '0';
		overflow = (char) (overflow+(('1'-result.charAt(0))&(operand.charAt(0)-'0')|(result.charAt(0)-'0')&('1'-operand.charAt(0))));
		return overflow+result;
	}
	
	/**
	 * 整数加法，要求调用{@link #claAdder(String, String, char) claAdder}方法实现。<br/>
	 * 例：integerAddition("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被加数
	 * @param operand2 二进制补码表示的加数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相加结果
	 */
	public String integerAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String formal1 = operand1;
		String formal2 = operand2;
		String result = "";
		
		for(int i = 0;i<length-operand1.length();i++){
			formal1 = operand1.charAt(0)+formal1;
		}
		
		for(int i = 0;i<length-operand2.length();i++){
			formal2 = operand2.charAt(0)+formal2;
		}
		
		char c = '0';
		for(int i = 0;i<length/4;i++){
			result = claAdder(formal1.substring(length-4*i-4,length-4*i),formal2.substring(length-4*i-4,length-4*i),c)+result;
			c = result.charAt(0);
			result = result.substring(1);
		}
		
		char overflow = '0';
		overflow = (char) (overflow+(('1'-result.charAt(0))&(formal1.charAt(0)-'0')&(formal2.charAt(0)-'0')|(result.charAt(0)-'0')&('1'-formal1.charAt(0))&('1'-formal2.charAt(0)-'0')));
		
		return overflow+result;
	}
	
	/**
	 * 整数减法，可调用{@link #integerAddition(String, String, char, int) integerAddition}方法实现。<br/>
	 * 例：integerSubtraction("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被减数
	 * @param operand2 二进制补码表示的减数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相减结果
	 */
	public String integerSubtraction (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String formal1 = operand1;
		String formal2 = negation(operand2);
		String result = "";
		
		for(int i = 0;i<length-operand1.length();i++){
			formal1 = operand1.charAt(0)+formal1;
		}
		
		for(int i = 0;i<length-operand2.length();i++){
			formal2 = operand2.charAt(0)+formal2;
		}
		
		char c = '1';
		for(int i = 0;i<length/4;i++){
			result = claAdder(formal1.substring(length-4*i-4,length-4*i),formal2.substring(length-4*i-4,length-4*i),c)+result;
			c = result.charAt(0);
			result = result.substring(1);
		}
		
		char overflow = '0';
		overflow = (char) (overflow+(('1'-result.charAt(0))&(formal1.charAt(0)-'0')&(formal2.charAt(0)-'0')|(result.charAt(0)-'0')&('1'-formal1.charAt(0))&('1'-formal2.charAt(0)-'0')));
		
		return overflow+result;
	}
	
	/**
	 * 整数乘法，使用Booth算法实现，可调用{@link #integerAddition(String, String, char, int) integerAddition}等方法。<br/>
	 * 例：integerMultiplication("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被乘数
	 * @param operand2 二进制补码表示的乘数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为length+1的字符串表示的相乘结果，其中第1位指示是否溢出（溢出为1，否则为0），后length位是相乘结果
	 */
	public String integerMultiplication (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		String formal1 = operand1;
		String formal2 = operand2;
		
		for(int i = 0;i<length-operand1.length();i++){
			formal1 = operand1.charAt(0)+formal1;
		}
		
		for(int i = 0;i<length-operand2.length();i++){
			formal2 = operand2.charAt(0)+formal2;
		}
		
		String product = formal2+"0";
		
		for(int i = 0;i<length;i++)
			product = "0" + product; 
		
		for(int i = 0;i<length;i++){
			String part = product.substring(0,length);
			//比较P0和P1的值
			if(product.charAt(2*length)-product.charAt(2*length-1)==1){
				part = integerAddition(part,formal1,length).substring(1);
			}else if(product.charAt(2*length)-product.charAt(2*length-1)==-1){
				part = integerSubtraction(part,formal1,length).substring(1);
			}
			product = part+product.substring(length);
			product = ariRightShift(product,1);
		}
		
		String result = product.substring(length,2*length);
		
		for(int i =0;i<length;i++){
			if(product.charAt(i)!=result.charAt(0)){
				return "1"+result;
			}
		}
		return "0"+result;
	}
	
	/**
	 * 整数的不恢复余数除法，可调用{@link #integerAddition(String, String, char, int) integerAddition}等方法实现。<br/>
	 * 例：integerDivision("0100", "0011", 8)
	 * @param operand1 二进制补码表示的被除数
	 * @param operand2 二进制补码表示的除数
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度，当某个操作数的长度小于length时，需要在高位补符号位
	 * @return 长度为2*length+1的字符串表示的相除结果，其中第1位指示是否溢出（溢出为1，否则为0），其后length位为商，最后length位为余数
	 */
	public String integerDivision (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 带符号整数加法，要求调用{@link #integerAddition(String, String, int) integerAddition}、{@link #integerSubtraction(String, String, int) integerSubtraction}等方法实现。
	 * 但符号的确定、结果是否修正等需要按照相关算法进行，不能直接转为补码表示后运算再转回来<br/>
	 * 例：signedAddition("1100", "1011", 8)
	 * @param operand1 二进制原码表示的被加数，其中第1位为符号位
	 * @param operand2 二进制原码表示的加数，其中第1位为符号位
	 * @param length 存放操作数的寄存器的长度，为4的倍数。length不小于操作数的长度（不包含符号），当某个操作数的长度小于length时，需要将其长度扩展到length
	 * @return 长度为length+2的字符串表示的计算结果，其中第1位指示是否溢出（溢出为1，否则为0），第2位为符号位，后length位是相加结果
	 */
	public String signedAddition (String operand1, String operand2, int length) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数加法，可调用{@link #integerAddition(String, String, char, int) intergerAddition}等方法实现。<br/>
	 * 例：floatAddition("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被加数
	 * @param operand2 二进制表示的加数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相加结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatAddition (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数减法，可调用{@link #floatAddition(String, String, int, int, int) floatAddition}方法实现。<br/>
	 * 例：floatSubtraction("00111111010100000", "00111111001000000", 8, 8, 8)
	 * @param operand1 二进制表示的被减数
	 * @param operand2 二进制表示的减数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @param gLength 保护位的长度
	 * @return 长度为2+eLength+sLength的字符串表示的相减结果，其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatSubtraction (String operand1, String operand2, int eLength, int sLength, int gLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数乘法，可调用{@link #integerAddition(String, String, char, int) integerAddition}等方法实现。<br/>
	 * 例：floatMultiplication("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被乘数
	 * @param operand2 二进制表示的乘数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatMultiplication (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
	
	/**
	 * 浮点数除法，可调用{@link #integerAddition(String, String, char, int) integerAddition}等方法实现。<br/>
	 * 例：floatDivision("00111110111000000", "00111111000000000", 8, 8)
	 * @param operand1 二进制表示的被除数
	 * @param operand2 二进制表示的除数
	 * @param eLength 指数的长度，取值大于等于 4
	 * @param sLength 尾数的长度，取值大于等于 4
	 * @return 长度为2+eLength+sLength的字符串表示的相乘结果,其中第1位指示是否指数上溢（溢出为1，否则为0），其余位从左到右依次为符号、指数（移码表示）、尾数（首位隐藏）。舍入策略为向0舍入
	 */
	public String floatDivision (String operand1, String operand2, int eLength, int sLength) {
		// TODO YOUR CODE HERE.
		return null;
	}
}
