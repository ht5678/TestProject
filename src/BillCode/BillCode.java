package BillCode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import dto.annotation.PropertyHelper;

import BillCode.service.SequenceServImpl;

public class BillCode {

	private static Map<String,BillCode> billcodes = new HashMap<String, BillCode>();
	private String className;
	private List<Piece> pieces;
	
	
	/**
	 * 构造函数
	 * @param element
	 */
	private BillCode(Element element){
		String index = element.attributeValue("index");
		if(element.attributeValue("index")!=null)
			setClassName(element.attributeValue("index")+"|"+element.attributeValue("className"));
		else
			setClassName(element.attributeValue("className").trim());
		
		for(Object o : element.elements("piece")){
			Element eb = (Element)o;
			String t = eb.attributeValue("type");
			Piece b = new Piece(getPieces(), index);
			for(PieceType pt : PieceType.values()){
				if(t.equalsIgnoreCase(pt.toString())){
					b.type = pt;
					b.value = eb.attributeValue("value");
					if(eb.attributeValue("hide")==null || !"true".equalsIgnoreCase(eb.attributeValue("hide")))
						b.hide = false;
					else
						b.hide = true;
					if(pt == PieceType.MAP)
						for(Object oe : eb.elements("entry")){
							Element ee = (Element)oe;
							b.getMap().put(ee.attributeValue("key"), ee.attributeValue("value"));
						}
					
					for(Object of : eb.elements("func")){
						Element ef = (Element)of;
						String ft = ef.attributeValue("name");
						for(FunctionType fut : FunctionType.values()){
							if(fut.toString().equalsIgnoreCase(ft))
								b.getFunction().add(new Function(fut, ef.attributeValue("parameter")));
						}
					}
					
					getPieces().add(b);
				}
			}
		}
		
	}
	
	
	/**
	 * 对象转换成字符串
	 * @param value
	 * @return
	 */
	private static String readValue(Object value){
		if(value == null)
			return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(value instanceof java.util.Date || value instanceof java.sql.Date){
			if(value instanceof java.util.Date){
				String str = sdf2.format(value);
				if(!str.subSequence(11, 16).equals(str)){
					return str;
				}
				return sdf.format(value);
			}
		}else if(value instanceof java.util.Calendar){
			Calendar c = (Calendar)value;
			return sdf.format(c.getTime());
		}
		return value.toString();
	}
	
	/**
	 * 根据对象按照编码规则获取编码
	 * @return
	 */
	public String getCodeByObject(Object object){
		StringBuffer sb = new StringBuffer();
		for(Piece p : getPieces()){
			sb.append(p.getCode(object));
		}
		return sb.toString();
	}
	
	/**
	 * 根据编码名称及对象按照编码规则获取编码
	 * @param className
	 * @param object
	 * @return
	 */
	private static String getCode(String className , Object object){
		if(object ==null){
			return "BillCode对象参数为空";
		}
		BillCode bc =	getBillCodes().get(className);
		if(bc==null)
			return null;
		return bc.getCodeByObject(object);
	}
	
	/**
	 * 使用对象的class及对象按照编码规则获取编码，例如：BillCode.getCodeByClass(bean.class,bean);
	 * @param c
	 * @param object
	 * @return
	 */
	public static String getCodeByClass(Class<?> c , Object object){
		String ret = getCode(c.getName(),object);
		return null;
	}
	
	
	/**
	 * 根据对象按照编码规则获取编码
	 * @param object
	 * @return
	 */
	public static String getCode(Object object){
		return getCodeByClass(object.getClass(), object);
	}
	
	/**
	 * 代码组成类型
	 * @author byht
	 *
	 */
	private enum PieceType{
		DATE,SEQUENCE,PROPERTY,UUID,MAP,SYSTEM
	}
	
	/**
	 * 获取编码规则中的代码组成
	 * 
	 * @return the blocks
	 */
	private List<Piece> getPieces() {
		if (pieces == null)
			pieces = new ArrayList<Piece>();
		return pieces;
	}
	
	/**
	 * 获取所有编码规则
	 * @return
	 */
	private static Map<String, BillCode> getBillCodes(){
		return billcodes;
	}
	
	/**
	 * 增加编码规则
	 * 
	 * @param billCode
	 */
	public static void addBillCode(Element element){
		BillCode bc = new BillCode(element);
		getBillCodes().put(bc.getClassName(), bc);
	}
	
	/**
	 * 
	 * @return	the class name
	 */
	public String getClassName(){
		return this.className;
	}
	
	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
	private class Piece{
		private PieceType type;
		private String value;
		private boolean hide;
		private Map<String,String> map;
		private List<BillCode.Function> function;
		private List<Piece> pieces;
		private Integer index;
		
		private Piece(List<Piece> pieces){
			this.pieces = pieces;
		}
		
		private Piece(List<Piece> pieces , Integer index){
			this.pieces = pieces;
			this.index = index;
		}
		
		private Piece(List<Piece> pieces , String index){
			this.pieces = pieces;
			if(index!=null)
				this.index = Integer.parseInt(index);
		}
		
		/**
		 * 执行函数
		 * @param str
		 * @return
		 */
		private String func(Object str){
			Object ret = str;
			if(function !=null){
				for(Function f : getFunction()){
					ret = f.execute(str);
				}
			}
			return ret.toString();
		}
		
		String getHideCode(Object obj){
			Object ret = value;
			switch(type){
				case UUID:
					ret = UUID.randomUUID().toString();
					break;
				case DATE:
					if(value == null || value.length()==0){
						ret = new Date();
					}else{
						ret = new SimpleDateFormat(value).format(new Date());
					}
					break;
				case PROPERTY:
					try{
						ret = PropertyHelper.getProperty(obj, value);
					}catch(Exception e){
						e.printStackTrace();
						ret = "";
					}
					break;
				case MAP:
					try{
						ret = getMap().get(BillCode.readValue(PropertyHelper.getSimpleProperty(obj, value)));
					}catch(Exception e){
						e.printStackTrace();
						ret = "";
					}
					break;
				case SEQUENCE:
					SequenceServImpl sequence = new SequenceServImpl();
					if(value ==null || value.length()==0)
						if(index == null)
							ret = sequence.getInteger(obj.getClass().getName());
						else
							ret = sequence.getInteger(obj.getClass().getName()+"-"+index);
					else{
						StringBuffer sb = new StringBuffer();
						//TODO:不懂
						for(String s : value.toString().split(","))
							if(!pieces.get(Integer.valueOf(s)).equals(this)){
								sb.append(pieces.get(Integer.valueOf(s)).getHideCode(obj));
							}
						if(index == null){
							ret = sequence.getInteger(obj.getClass().getName());
						}else{
							ret = sequence.getInteger(obj.getClass().getName()+"-"+index);
						}
					}
					break;
				case SYSTEM:
					System.out.println("没有实现");
					break;
			}
			return func(ret);
		}
		/**
		 * 获取区段代码
		 * 
		 * @param object
		 * @return
		 */
		String getCode(Object object) {
			if (hide)
				return "";
			return getHideCode(object);
		}

		/**
		 * @return the map
		 */
		Map<String, String> getMap() {
			if (map == null)
				map = new HashMap<String, String>();
			return map;
		}

		/**
		 * @return the function
		 */
		List<Function> getFunction() {
			if (function == null)
				function = new ArrayList<Function>();
			return function;
		}

		/**
		 * @return the hide
		 */
		public boolean isHide() {
			return hide;
		}
		
	}
	
	
	/**
	 * Function函数
	 * @author byht
	 *
	 */
	private class Function{
		private BillCode.FunctionType type;
		private String parameter;
		
		private Function(BillCode.FunctionType type , String parameter){
			this.type = type;
			this.parameter = parameter;
		}
		
		
		/**
		 * 解析编码处理函数，返回处理后的编码片段
		 * 
		 * @param value
		 * @return
		 */
		Object execute(Object value){
			String[] p = this.parameter.split(",");
			System.out.println("paramters :  "+parameter);
			System.out.println("paramter数组 ： "+p);
			//元框架中的参数   ---  $SWITCH_TABLE$org$wakeframework$extend$billcode$BillCode$FunctionType()[this.type.ordinal()]
			//猜测是某个方法的调用方法
			switch(type){
				case substring:
					if(p.length == 1){
						return String.valueOf(value).substring(Integer.valueOf(p[0]).intValue());
					}
					if(p.length>1){
						return String.valueOf(value).substring(Integer.valueOf(p[0]).intValue(),Integer.valueOf(p[1]).intValue());
					}
					return value.toString();
				case format:
					return String.format(this.parameter, new Object[]{value});
				case replace:
					if(p.length == 2)
						return StringUtils.replace(value.toString(), p[0], p[1]);
					if(p.length == 3)
						return StringUtils.replace(value.toString(), p[0], p[1] , Integer.valueOf(p[2]).intValue());break;
			}
			return value.toString();
		}

		
	}
	
	private static enum FunctionType{
		substring,format,replace
	}
	
}
