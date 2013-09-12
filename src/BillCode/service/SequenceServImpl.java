package BillCode.service;

import domain.Sequence;

public class SequenceServImpl implements SequenceServ{

	
	public Integer getInteger(String name) {
		return Sequence.getInteger(name);
	}

}
