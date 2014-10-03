package jp.enpit.cloud.eventspiral.view;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * <ol>
 * <li>HibernateValiadtor処理クラス</li>
 * </ol>
 */
public abstract class ValidatedBean {
	/**
	 * <ol>
	 *   <li>呼び元のクラス名のログをとる．</li>
	 *   <li>HibernateValiadtorを使用してバリデーションを行う．</li>
	 *   <li>制約違反が発生した場合は、TEMValidationExceptionを投げる．</li>
	 *   <li>制約違反がない場合はtrueを返す</li>
	 * </ol>
	 * @return boolean
	 * @throws TEMValidationException HibernateValiadtorの各制約違反が発生した場合
	 */
	public boolean validate() throws TEMValidationException{
		String className = getClass().getName();
		int index = className.lastIndexOf(".");
		className = className.substring(index + 1);
		Logger logger = Logger.getLogger(getClass().getName());
		logger.info(className + ".validate");

		try{
			return ValidatedBean.<ValidatedBean>validate(this);
		} catch(TEMValidationException e){
			logger.warning(className + ".validate: " + e.getMessage());
			throw e;
		}
	}

	private static <T extends ValidatedBean> boolean validate(T bean) throws TEMValidationException {
		// Hibernate Validator (HB) のvalidatorインスタンス生成
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		// バリデーション結果をConstraintViolationのSetとして取得する
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(bean);

		// 制約違反発見
		if (constraintViolations.size() > 0) {
			Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();

			throw new TEMValidationException(iterator.next().getMessage());
		}
		return true;
	}
}
