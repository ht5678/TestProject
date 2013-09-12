package dto.annotation;

public class DtoReaderModelImpl implements ClassReader {

	@Override
	public void read(Class<?> clazz) {
		if((clazz.getAnnotation(DtoClass.class)!=null)
				&&(!DtoHelper.checkDto(clazz)))
		System.out.println(clazz.getCanonicalName()+" 校验出现错误,请仔细检查");
	}

}
