package common;

import java.util.Iterator;

/**
 * implements something similar to Java standard class ArrayList<T>.
 * Not all functions are implemented.
 * Didn't care about performance or heap size or determinism because fuck it, it's java-like anyway.
 * 
 * @author joel
 *
 * @param <T> type to store in the list
 */
public class ShittyArrayList<T> implements Iterable<T> {
	private Object[] mData;
	private int mFirstEmptyIndex=0;
	
	/**
	 * initially the data array is instantiated with 5 elements. If more are required, the array doubles in size
	 */
	public ShittyArrayList(){
		mData = new Object[5];
	}
	
	/**
	 * @param aIndex	position of the required element
	 * @return			element at index aIndex
	 */
	@SuppressWarnings("unchecked")
	public T get(int aIndex){
		return (T)mData[aIndex];
	}

	/**
	 * appends an object to the list
	 * 
	 * @param aObject	thing that is to store
	 */
	public void add(T aObject){
		if(mFirstEmptyIndex>=mData.length){
			doubleSize();
		}
		mData[mFirstEmptyIndex++]=aObject;

	}
	
	/**
	 * insert an object into a certain position in the list.
	 * 
	 * @param aIndex	where should the object be inserted?
	 * @param aObject	the object to insert into the list
	 */
	public void add(int aIndex, T aObject){
		if(aIndex>=mFirstEmptyIndex){
			add(aObject);
			return; // fuck your indexing problems
		}
		if(mFirstEmptyIndex>=mData.length){
			doubleSize();
		}
		for(int i=mFirstEmptyIndex;i>aIndex;i--){
			//weird necessary workaround because reassigning of null is fucked up
			if(mData[i-1]==null){
				mData[i] = null;
			} else {
				mData[i]=mData[i-1];
			}
		}
		mData[aIndex]=aObject;
		mFirstEmptyIndex++;
	}
	

	/**
	 * removes element at index aIndex.
	 * 
	 * @param aIndex	index of element to remove
	 */
	public void remove(int aIndex) {
		if(aIndex >mFirstEmptyIndex-1){
			return;//fuck you
		}
		for(int i=aIndex; i<mFirstEmptyIndex-1;i++){
				mData[i]=mData[i+1];
		}
		mData[mFirstEmptyIndex-1]=null;
		mFirstEmptyIndex--;
	}
	
	/**
	 * @return	how many objects are in the list?
	 */
	public int getLength(){
		return mFirstEmptyIndex;
	}


	/* (non-Javadoc)
	 * @see java.lang.Iterable#iterator()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Iterator<T> iterator() {
		return new Iterator<T>(){
			private int count =0;
			public boolean hasNext() {return count<mFirstEmptyIndex;}
			public T next() {return (T)mData[count++];}
			public void remove() {ShittyArrayList.this.remove(count);}
		};
	}

	/**
	 * if the array is too short, it doubles in size. instantiating and copying array
	 * may cause severe CPU time burning but not giving a fuck.
	 */
	private void doubleSize(){
		// heap? stack? cpu burning? fuck it!
		Object[] tempArray = new Object[mData.length*2];
		for(int i=0; i<mFirstEmptyIndex;i++){
			tempArray[i]=mData[i];
		}
		mData=tempArray;
	}

}
