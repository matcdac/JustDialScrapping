package main.java.JustDialMultiServiceThreadsControlledParallelStream;

import java.lang.Thread.State;
import java.util.ArrayList;
import java.util.List;

public class ThreadsControl {
	
	private List<Runnable> listOfRunnable;
	private List<Thread> threads;
	private byte maxThreadsToBeActive;
	private byte currentActiveThreads;
	
	public ThreadsControl(byte maxThreadsToBeActive) {
		listOfRunnable = new ArrayList<Runnable>();
		threads = new ArrayList<Thread>();
		currentActiveThreads = (byte)0;
		this.maxThreadsToBeActive = maxThreadsToBeActive;
	}
	
	public void add(Runnable code) {
		listOfRunnable.add(code);
	}
	
	private void startOneThreadFromList() {
		Runnable code = listOfRunnable.get(0);
		listOfRunnable.remove(0);
		Thread thread = new Thread(code);
		thread.start();
		threads.add(thread);
		currentActiveThreads++;
	}
	
	private void checkForTerminatedThreadsAndRemoveThemFromList() {
		for(Thread thread : threads) {
			State state = thread.getState();
			if(Thread.State.TERMINATED.compareTo(state)==0) {
				threads.remove(thread);
				currentActiveThreads--;
				break;
			}
		}
	}
	
	public void start() {
		while(listOfRunnable.size() > 0) {
			while(currentActiveThreads<maxThreadsToBeActive || (listOfRunnable.size()>0 && maxThreadsToBeActive>listOfRunnable.size()) ) {
				startOneThreadFromList();
			}
			if(currentActiveThreads <= maxThreadsToBeActive) {
				checkForTerminatedThreadsAndRemoveThemFromList();
			}
		}
	}
	
}
