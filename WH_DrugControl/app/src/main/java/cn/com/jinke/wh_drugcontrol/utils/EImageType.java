package cn.com.jinke.wh_drugcontrol.utils;

/**
 * ͼ������
 * 
 * @author Usniyo
 */
public enum EImageType
{
	/** .jpg */
	JPEG(0),
	/** .png */
	PNG(1),
	/** .png Բ�� */
	ROUNDED_IMAGE(2),
	/** Head,С���С�ԭͼ��һ�� */
	HEAD(3);

	private final int mType;

	private EImageType(int type)
	{
		mType = type;
	}

	public int toValue()
	{
		return mType;
	}
}
