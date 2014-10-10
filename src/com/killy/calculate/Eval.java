package com.killy.calculate;

/**
 * Eval.java
 *
 * Created on 2006��4��10��, ����3:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 *
 * ֧���������+ - * / % > < ] [ ! | & =
 *      ���У�
 *           ]  ��ʾ���ڵ���
 *           [  ��ʾС�ڵ���
 *           �� ��ʾ������
 *           �� ��ʾ��
 *           �� ��ʾ��
 *           �� ��ʾ�Ƿ����
 *
 * ֧�ֺ�����sqrt,square,ceil,sin,cos,asin,acon.tan.atan,log,exp ���庬��� calFunction ����
 *
 */

/**
 *
 * @author Trumplet
 *
 */

import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Eval {
	public static String OPTS = "+-*/%><][!|&=#";

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object calculate(String expression) throws ExpressionException {
		try {
			Stack Opts = new Stack();
			Stack Values = new Stack();
			String exp = expression + "#";
			int nCount = exp.length(), nIn, nOut, nTemp;
			Opts.push("#");
			String temp = "", optIn = "", value1 = "", value2 = "", opt = "", temp1 = "";
			int nFun = 0;
			boolean isFun = false;
			for (int i = 0; i < nCount;) {
				nTemp = 0;
				opt = exp.substring(i, i + 1);
				isFun = false;
				temp1 = "";
				while (i < nCount) {
					if (!temp1.equals("")) {
						if (opt.equals("(")) {
							nFun++;
							isFun = true;
						} else if (opt.equals(")")) {
							nFun--;
						}
					}
					if ((nFun > 0) || ((!isFun) && this.isValue(opt))) {
						temp1 += opt;
						nTemp++;
						opt = exp.substring(i + nTemp, i + nTemp + 1);
					} else {
						if (isFun) {
							temp1 += opt;
							nTemp++;
						}
						break;
					}
				}
				if (temp1.equals("")) {
					temp = opt;
				} else {
					temp = temp1;
				}
				if (nTemp > 0) {
					i = i + nTemp - 1;
				}
				temp = temp.trim();

				if (this.isValue(temp)) {
					temp = this.getValue(temp);
					Values.push(temp);
					i++;
				} else {
					optIn = Opts.pop().toString();
					nIn = this.getOptPriorityIn(optIn);
					nOut = this.getOptPriorityOut(temp);
					if (nIn == nOut) {
						i++;
					} else if (nIn > nOut) {
						String ret = "";
						value1 = Values.pop().toString();
						value2 = Values.pop().toString();
						ret = String.valueOf(this.calValue(value2, optIn,
								value1));
						Values.push(ret);
					} else if (nIn < nOut) {
						Opts.push(optIn);
						Opts.push(temp);
						i++;
					}
				}
			}
			return Values.pop();
		} catch (ExpressionException eE) {
			throw eE;
		} catch (Exception e) {
			throw new ExpressionException("���ʽ" + expression + "��ʽ�Ƿ�!");
		}
	}

	protected int getOptPriorityOut(String opt) throws ExpressionException {
		if (opt.equals("+")) {
			return 1;
		} else if (opt.equals("-")) {
			return 2;
		} else if (opt.equals("*")) {
			return 5;
		} else if (opt.equals("/")) {
			return 6;
		} else if (opt.equals("%")) {
			return 7;
		} else if (opt.equals(">")) {
			return 11;
		} else if (opt.equals("<")) {
			return 12;
		} else if (opt.equals("]")) {
			return 13;
		} else if (opt.equals("[")) {
			return 14;
		} else if (opt.equals("!")) {
			return 15;
		} else if (opt.equals("|")) {
			return 16;
		} else if (opt.equals("&")) {
			return 23;
		} else if (opt.equals("=")) {
			return 25;
		} else if (opt.equals("#")) {
			return 0;
		} else if (opt.equals("(")) {
			return 1000;
		} else if (opt.equals(")")) {
			return -1000;
		}
		throw new ExpressionException("�����" + opt + "�Ƿ�!");
	}

	protected int getOptPriorityIn(String opt) throws ExpressionException {
		if (opt.equals("+")) {
			return 3;
		} else if (opt.equals("-")) {
			return 4;
		} else if (opt.equals("*")) {
			return 8;
		} else if (opt.equals("/")) {
			return 9;
		} else if (opt.equals("%")) {
			return 10;
		} else if (opt.equals(">")) {
			return 17;
		} else if (opt.equals("<")) {
			return 18;
		} else if (opt.equals("]")) {
			return 19;
		} else if (opt.equals("[")) {
			return 20;
		} else if (opt.equals("!")) {
			return 21;
		} else if (opt.equals("|")) {
			return 22;
		} else if (opt.equals("&")) {
			return 24;
		} else if (opt.equals("=")) {
			return 26;
		} else if (opt.equals("(")) {
			return -1000;
		} else if (opt.equals(")")) {
			return 1000;
		} else if (opt.equals("#")) {
			return 0;
		}
		throw new ExpressionException("�����" + opt + "�Ƿ���");
	}

	protected String getOPTS() {
		return OPTS;
	}

	protected boolean isValue(String cValue) {
		String notValue = this.getOPTS() + "()";
		return notValue.indexOf(cValue) == -1;
	}

	protected boolean isOpt(String value) {
		return this.getOPTS().indexOf(value) >= 0;
	}

	protected double calValue(String value1, String opt, String value2)
			throws ExpressionException {
		try {
			double dbValue1 = Double.valueOf(value1).doubleValue();
			double dbValue2 = Double.valueOf(value2).doubleValue();
			long lg = 0;
			if (opt.equals("+")) {
				return dbValue1 + dbValue2;
			} else if (opt.equals("-")) {
				return dbValue1 - dbValue2;
			} else if (opt.equals("*")) {
				return dbValue1 * dbValue2;
			} else if (opt.equals("/")) {
				return dbValue1 / dbValue2;
			} else if (opt.equals("%")) {
				lg = (long) (dbValue1 / dbValue2);
				return dbValue1 - lg * dbValue2;
			} else if (opt.equals(">")) {
				if (dbValue1 > dbValue2)
					return 1;
				else
					return 0;
			} else if (opt.equals("<")) {
				if (dbValue1 < dbValue2)
					return 1;
				else
					return 0;
			} else if (opt.equals("]")) {
				if (dbValue1 >= dbValue2)
					return 1;
				else
					return 0;
			} else if (opt.equals("[")) {
				if (dbValue1 <= dbValue2)
					return 1;
				else
					return 0;
			} else if (opt.equals("!")) {
				if (dbValue1 != dbValue2)
					return 1;
				else
					return 0;
			} else if (opt.equals("|")) {
				if (dbValue1 > 0 || dbValue2 > 0)
					return 1;
				else
					return 0;
			} else if (opt.equals("&")) {
				if (dbValue1 > 0 && dbValue2 > 0)
					return 1;
				else
					return 0;
			} else if (opt.equals("=")) {
				if (dbValue1 == dbValue2)
					return 1;
				else
					return 0;
			}
		} catch (Exception e) {
			throw new ExpressionException("ֵ" + value1 + "��" + value2 + "�ڽ���"
					+ opt + "����ʱ�Ƿ���");
		}
		throw new ExpressionException("�����" + opt + "�Ƿ���");
	}

	protected String getValue(String oldValue) throws ExpressionException {
		String reg = "^([a-zA-Z0-9_]+)\\(([a-zA-Z0-9_.()]+)\\)$";
		if (this.isFunctionCal(oldValue)) {
			Pattern p = Pattern.compile(reg);
			Matcher m = p.matcher(oldValue);
			m.find();
			return calFunction(m.group(1), m.group(2));
		}
		return oldValue;
	}

	protected boolean isFunctionCal(String value) {
		String reg = "^([a-zA-Z0-9_]+)\\(([a-zA-Z0-9_.()]+)\\)$";
		return value.matches(reg);
	}

	protected String calFunction(String function, String value)
			throws ExpressionException {
		String lowerFun = function.toLowerCase();
		double db = 0;
		try {
			db = Double.valueOf(this.getValue(value)).doubleValue();
			if (lowerFun.equals("log")) {
				return String.valueOf(Math.log(db));
			} else if (lowerFun.equals("square")) {
				return String.valueOf(Math.pow(db, 2));
			} else if (lowerFun.equals("sqrt")) {
				return String.valueOf(Math.sqrt(db));
			} else if (lowerFun.equals("sin")) {
				return String.valueOf(Math.sin(db));
			} else if (lowerFun.equals("asin")) {
				return String.valueOf(Math.asin(db));
			} else if (lowerFun.equals("cos")) {
				return String.valueOf(Math.cos(db));
			} else if (lowerFun.equals("tan")) {
				return String.valueOf(Math.tan(db));
			} else if (lowerFun.equals("atan")) {
				return String.valueOf(Math.atan(db));
			} else if (lowerFun.equals("ceil")) {
				return String.valueOf(Math.ceil(db));
			} else if (lowerFun.equals("exp")) {
				return String.valueOf(Math.exp(db));
			}
		} catch (Exception e) {
			throw new ExpressionException("����" + function + "ֵ" + value + "�Ƿ�!");
		}

		throw new ExpressionException("����" + function + "��֧�֣�");
	}

	// ���ʽ�쳣����룺
	public class ExpressionException extends Exception {
		/**
		 * 
		 */
		private static final long serialVersionUID = -4757524666694772911L;

		public ExpressionException(String msg) {
			super(msg);
		}
	}
}