package com.faRegex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.javatuples.Triplet;

import com.faRegex.core.ComportamentalFA;
import com.faRegex.core.ComportamentalFANetwork;
import com.faRegex.core.Link;
import com.faRegex.core.OutTransition;
import com.faRegex.core.State;

public class ComportamentalFANSpace {
	private ComportamentalFANetwork compFAN;
	private List<SpaceState> spaceStates;
	
	public ComportamentalFANSpace(ComportamentalFANetwork compFAN) {
		spaceStates = new ArrayList<SpaceState>();
		this.compFAN = compFAN;
	}
	
	public List<SpaceState> getStates() {
		return spaceStates;
	}
	
	public void build() {
		initialize();
		
		for(int index=0; index<spaceStates.size(); index++) {
			SpaceState actualSpaceState= spaceStates.get(index);
			HashMap<State, List<OutTransition>> nextTransitionPerState= actualSpaceState.nextTransitionPerState();
			addStates(actualSpaceState, nextTransitionPerState);
		}
		
		prune();
	}

	/**
	 * This method is particularly crucial!
	 * This preserves the state order across all the spaceStates!
	 * @param actualSpaceState
	 * @param nextTransitionPerState
	 */
	private void addStates(SpaceState actualSpaceState, HashMap<State, List<OutTransition>> nextTransitionPerState) {
		int numComportamentalFA=0;
		
		for (State actualState: actualSpaceState.getStates()) {
			List<OutTransition> actualOutTransList= nextTransitionPerState.get(actualState);
			if (actualOutTransList != null)
				for (OutTransition actualOutTrans: actualOutTransList) {
					State newState= null;
					for(State candidate: compFAN.getComportamentalFAs()[numComportamentalFA].getStates())
						if (actualOutTrans.getDestination().equals(candidate.getName())) {
							newState= candidate;
							break;
						}
					
					SpaceState newSpaceState= newState(actualSpaceState, actualState, newState, actualOutTrans);
					int existsIndex= spaceStates.indexOf(newSpaceState);
					if (existsIndex == -1) {
						spaceStates.add(newSpaceState);
						actualSpaceState.addNext(actualOutTrans, newSpaceState);
					} else
						actualSpaceState.addNext(actualOutTrans, spaceStates.get(existsIndex));
				}
			numComportamentalFA++;
		}
	}
	
	private void prune() {
		ArrayList<SpaceState> mantain= new ArrayList<SpaceState>();
		ArrayList<SpaceState> remove= new ArrayList<SpaceState>();
		for(SpaceState state: spaceStates)
			if (!mantain.contains(state) && !remove.contains(state))
				pruneRecursive(state, new ArrayList<Triplet<SpaceState, String, SpaceState>>(), mantain, remove);
		
		spaceStates= mantain;
	}
	
	private boolean pruneRecursive(SpaceState state, List<Triplet<SpaceState, String, SpaceState>> forbidden
			, List<SpaceState> mantain, List<SpaceState> remove) {
		if (state.isFinal()) {
			if(!mantain.contains(state))
				mantain.add(state);
			return false;
		}
		else {
			HashMap<OutTransition, SpaceState> stateNext= state.getNext();
			for (OutTransition outTrans: stateNext.keySet()) {
				SpaceState next= stateNext.get(outTrans);
				if(!remove.contains(next)) {
					Triplet<SpaceState, String, SpaceState> arc= Triplet.with(state, outTrans.getName(), next);
					
					if(!forbidden.contains(arc)) {
						forbidden.add(Triplet.with(state, outTrans.getName(), next));
						if (mantain.contains(next)) {
							if(!mantain.contains(state))
								mantain.add(state);
							return false;
						} else if (!pruneRecursive(next, forbidden, mantain, remove)) {
								if(!mantain.contains(state))
									mantain.add(state);
								return false;
						}
					}
				}
			}
			remove.add(state);
			return true;
		}
	}
	
	private SpaceState newState(SpaceState oldSpace, State oldState, State newState, OutTransition outTransition) {
		SpaceState newSpace= (SpaceState) oldSpace.clone();
		for (Link link: outTransition.getLink())
			if(link.getType().equals(Link.Type.OUT))
				newSpace.setLink(link.getLink(), link.getEvent());
			else
				newSpace.clearLink(link.getLink());
				
		newSpace.changeState(oldState, newState);
		
		return newSpace;
	}
	
	private SpaceState initialize() {
		ComportamentalFA[] compFAs = compFAN.getComportamentalFAs();
		String[] linksNames = compFAN.getLinksNames();
		List<State> initStates= Arrays.stream(compFAs).map(compFA -> compFA.initState()).collect(Collectors.toList());
		SpaceState init= new SpaceState(initStates, linksNames);
		spaceStates.clear();
		spaceStates.add(init);
		return init;
	}
}
