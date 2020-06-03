package top.zcwfeng.customui.test;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;

public class Apple<T extends Object> implements Externalizable

{

	private T info;

	public Apple(){}

	public Apple(T info)

	{

		this.info = info;

	}

	public void setInfo(T info)

	{

		this.info = info;

	}

	public T getInfo()

	{

		return this.info;

	}

	public static void main(String[] args) {


		ObjectInputStream in;



	}


	@Override
	public void writeExternal(ObjectOutput out) throws IOException {

	}

	@Override
	public void readExternal(ObjectInput in) throws ClassNotFoundException, IOException {

	}
}
