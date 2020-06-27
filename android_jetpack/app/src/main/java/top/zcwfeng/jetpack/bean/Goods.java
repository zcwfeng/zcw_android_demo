package top.zcwfeng.jetpack.bean;

/**
 * 实体
 */
public class Goods {

	public int icon;
	public String like;
	public String style;

	public Goods() {
	}

	public Goods(int icon, String like, String style) {
		super();
		this.icon = icon;
		this.like = like;
		this.style = style;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getLike() {
		return like;
	}

	public void setLike(String like) {
		this.like = like;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

}