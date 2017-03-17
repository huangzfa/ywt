package org.com.huang.entity;
//--表示一级菜单
public class ComplexButton {
	private String name;//菜单名称
	private Button[] sub_button;//子级菜单，因为一个一级菜单可以有多个二级菜单，所以这儿使用二级 菜单类型的集合
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Button[] getSub_button() {
		return sub_button;
	}
	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
}
