package xml.xsd;

/**
 * <DL><DT><B>功能名：</B><DD>XSD辅助类</DL>
 * <DL><DT><B>类描述：</B><DD>辅助类</DL>
 * @author 岳志华
 */
public class XsdHelper {
	
	/*----------------------------文件路径--------------------------*/

	/*-- 原始舱单 --*/
	
	/**
	 * 原始舱单-主要数据-XSD文件路径
	 */
	public static final String IMP_M_F_FIRST_URL = XsdHelper.class.getResource("Manifest_Declare_Import_Ship_First_1101_9.xsd").getPath();

	/**
	 * 原始舱单-次要数据-XSD文件路径
	 */
	public static final String IMP_M_F_SECONDLY_URL = XsdHelper.class.getResource("Manifest_Declare_Import_Ship_Secondly_1101_0.xsd").getPath();

	/**
	 * 原始舱单-修改数据-XSD文件路径
	 */
	public static final String IMP_M_F_EDIT_URL = XsdHelper.class.getResource("Manifest_Declare_Import_Ship_Edit_1101_5.xsd").getPath();

	/**
	 * 原始舱单-删除数据-XSD文件路径
	 */
	public static final String IMP_M_F_DELETE_URL = XsdHelper.class.getResource("Manifest_Declare_Import_Ship_Delete_1101_3.xsd").getPath();

	/*-- 预配舱单 --*/
	
	/**
	 * 预配舱单-主要数据-XSD文件路径
	 */
	public static final String EXP_M_F_FIRST_URL = XsdHelper.class.getResource("Manifest_Declare_Export_Ship_First_2101_9.xsd").getPath();

	/**
	 * 预配舱单-次要数据-XSD文件路径
	 */
	public static final String EXP_M_F_SECONDLY_URL = XsdHelper.class.getResource("Manifest_Declare_Export_Ship_Secondly_2101_0.xsd").getPath();

	/**
	 * 预配舱单-修改数据-XSD文件路径
	 */
	public static final String EXP_M_F_EDIT_URL = XsdHelper.class.getResource("Manifest_Declare_Export_Ship_Edit_2101_5.xsd").getPath();

	/**
	 * 预配舱单-删除数据-XSD文件路径
	 */
	public static final String EXP_M_F_DELETE_URL = XsdHelper.class.getResource("Manifest_Declare_Export_Ship_Delete_2101_3.xsd").getPath();

	/*-- 装载舱单 --*/
	
	/**
	 * 装载舱单-申报数据-XSD文件路径
	 */
	public static final String LOAD_M_F_NEW_URL = XsdHelper.class.getResource("Manifest_Load_Ship_4101_2.xsd").getPath();

	/**
	 * 装载舱单-删除数据-XSD文件路径
	 */
	public static final String LOAD_M_F_DELETE_URL = XsdHelper.class.getResource("Manifest_Load_Ship_Delete_4101_3.xsd").getPath();

	/*-- 进口空箱申请 --*/
	
	/**
	 * 进口空箱申请-删除数据-XSD文件路径
	 */
	public static final String IMP_EMP_NEW_URL = XsdHelper.class.getResource("Manifest_EmptyConta_Export_ECEX_2.xsd").getPath();

	/*-- 出口空箱申请 --*/
	
	/**
	 * 出口空箱申请-删除数据-XSD文件路径
	 */
	public static final String EXP_EMP_NEW_URL = XsdHelper.class.getResource("Manifest_EmptyConta_Import_ECIM_2.xsd").getPath();
}
