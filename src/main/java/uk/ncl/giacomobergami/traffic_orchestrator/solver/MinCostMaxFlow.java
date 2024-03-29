/*
 * MinCostMaxFlow.java
 * This file is part of SimulatorBridger-central_agent_planner
 *
 * Copyright (C) 2022 - Giacomo Bergami
 *
 * SimulatorBridger-central_agent_planner is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * SimulatorBridger-central_agent_planner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SimulatorBridger-central_agent_planner. If not, see <http://www.gnu.org/licenses/>.
 */

package uk.ncl.giacomobergami.traffic_orchestrator.solver;
// Java Program to implement
// the above approach
/// https://www.geeksforgeeks.org/minimum-cost-maximum-flow-from-a-graph-using-bellman-ford-algorithm/
import uk.ncl.giacomobergami.utils.structures.ImmutablePair;

import java.util.*;

public class MinCostMaxFlow {
	static class Edge {
		int to, f, cap, cost, rev;

		Edge(int v, int cap, int cost, int rev) {
			this.to = v;
			this.cap = cap;
			this.cost = cost;
			this.rev = rev;
		}
	}

	public static List<Edge>[] createGraph(int n) {
		List<Edge>[] graph = new List[n];
		for (int i = 0; i < n; i++)
			graph[i] = new ArrayList<Edge>();
		return graph;
	}

	public static void addEdge(List<Edge>[] graph, int s, int t, int cap, int cost) {
		graph[s].add(new Edge(t, cap, cost, graph[t].size()));
		graph[t].add(new Edge(s, 0, -cost, graph[s].size() - 1));
	}

	// Stores the found edges
	boolean found[];

	// Stores the number of nodes
	int N;

	// Stores the capacity
	// of each edge
	int cap[][];

	int flow[][];

	// Stores the cost per
	// unit flow of each edge
	int cost[][];

	// Stores the distance from each node
	// and picked edges for each node
	int dad[], dist[], pi[];

	public static final int INF
			= Integer.MAX_VALUE / 2 - 1;

	// Function to check if it is possible to
	// have a flow from the src to sink
	boolean search(int src, int sink)
	{
		// Initialise found[] to false
		Arrays.fill(found, false);

		// Initialise the dist[] to INF
		Arrays.fill(dist, INF);

		// Distance from the source node
		dist[src] = 0;

		// Iterate until src reaches N
		while (src != N) {

			int best = N;
			found[src] = true;

			for (int k = 0; k < N; k++) {

				// If already found
				if (found[k])
					continue;

				// Evaluate while flow
				// is still in supply
				if (flow[k][src] != 0) {

					// Obtain the total value
					int val
							= dist[src] + pi[src]
							- pi[k] - cost[k][src];

					// If dist[k] is > minimum value
					if (dist[k] > val) {

						// Update
						dist[k] = val;
						dad[k] = src;
					}
				}

				if (flow[src][k] < cap[src][k]) {

					int val = dist[src] + pi[src]
							- pi[k] + cost[src][k];

					// If dist[k] is > minimum value
					if (dist[k] > val) {

						// Update
						dist[k] = val;
						dad[k] = src;
					}
				}

				if (dist[k] < dist[best])
					best = k;
			}

			// Update src to best for
			// next iteration
			src = best;
		}

		for (int k = 0; k < N; k++)
			pi[k]
					= Math.min(pi[k] + dist[k],
					INF);

		// Return the value obtained at sink
		return found[sink];
	}

	public HashMap<ImmutablePair<Integer, Integer>, List<Integer>> map = null;
	HashSet<Integer> vis  = null;
	double[] d = null;
	boolean[] b = null;
	int[] T = null;

	public void bellman_ford_moore(int r)
	{
		// Saving time for continuous memory allocation
		if (map == null) map = new HashMap<>();
		if (vis == null) vis = new HashSet<>();
		if (T == null) T = new int[N];
		if (b == null) b = new boolean[N];
		if (d == null) d = new double[N];
		if (!vis.add(r)) return; // Not performing any computation if we arlready performed the
		// shortest path on this at some point

		for (int u = 0; u<N; u++) {
			if (u != r) {
				T[u] = -1;
				d[u] = Integer.MAX_VALUE;
				b[u] = false;
			} else {
				T[r] = -1;
				d[r] = 0;
				b[r] = true;
			}
		}

		Queue<Integer> S = new LinkedList<>();
		S.add(r);
		while (!S.isEmpty()) {
			var u = S.poll();
			b[u] = false;
			for (int v = 0; v<N; v++) {
				if (cost[u][v] > 0) {
					if (d[u] + cost[u][v] < d[v]) {
						if (!b[v]) {
							S.add(v);
							b[v] = true;
						}
						T[v] = u;
						d[v] = d[u] + cost[u][v];
					}
				}
			}
		}

		for (int target = 0; target < N; target++) {
			var actualTarget = target;
			var visitingTarget = target;
			List<Integer> inversePath = new ArrayList<>(N);
			while (T[visitingTarget] != -1) {
				inversePath.add(visitingTarget);
				visitingTarget = T[visitingTarget];
			}
			if (!inversePath.isEmpty()) {
				if (visitingTarget != r) {
					throw new RuntimeException("ERROR IN THE ALGORITHM: all the pathrs should lead to the source!");
				}
				inversePath.add(r);
				Collections.reverse(inversePath);
				map.put(new ImmutablePair<>(r, actualTarget), inversePath);
			}
		}
	}

	public static class Result {
		public double total_flow;
		public double total_cost;
		public Set<ImmutablePair<Integer, List<Integer>>> minedPaths;

		public Result(double total_flow, double total_cost, Set<ImmutablePair<Integer, List<Integer>>> hsl) {
			this.total_flow = total_flow;
			this.total_cost = total_cost;
			this.minedPaths = hsl;
		}

		@Override
		public String toString() {
			return "Result{" +
					"total_flow=" + total_flow +
					", total_cost=" + total_cost +
					'}';
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Result result = (Result) o;
			return Double.compare(result.total_flow, total_flow) == 0 && Double.compare(result.total_cost, total_cost) == 0;
		}

		@Override
		public int hashCode() {
			return Objects.hash(total_flow, total_cost);
		}
	}

	// Function to obtain the maximum Flow
	public Result getMaxFlow(int[][] cap, int[][] cost, int src, int sink, int maxFlow) {
		this.cap = cap;
		this.cost = cost;

		N = cap.length;
		found = new boolean[N];
		flow = new int[N][N];
		dist = new int[N + 1];
		dad = new int[N];
		pi = new int[N];

		Set<ImmutablePair<Integer, List<Integer>>> hsl = new HashSet<>();
		int totflow = 0, totcost = 0;

		// If a path exist from src to sink
		while (search(src, sink)) {
			int localCost = 0;
			// Set the default amount
			int amt = INF;
			List<Integer> path = new ArrayList<>();
			for (int x = sink; x != src; x = dad[x])

				amt = Math.min(amt,
						flow[x][dad[x]] != 0
								? flow[x][dad[x]]
								: cap[dad[x]][x]
								- flow[dad[x]][x]);

			for (int x = sink; x != src; x = dad[x]) {
				if (x != sink) path.add(x);
				if (flow[x][dad[x]] != 0) {
					flow[x][dad[x]] -= amt;
					localCost -= amt * cost[x][dad[x]];
				}
				else {
					flow[dad[x]][x] += amt;
					localCost += amt * cost[dad[x]][x];
				}
			}
			totcost += localCost;
			Collections.reverse(path);
			hsl.add(new ImmutablePair<>(localCost, path));
			totflow += amt;
			maxFlow -= amt;
			if (maxFlow <= 0) break;
		}


		// Return pair total cost and sink
		return new Result(totflow,totcost,hsl);
	}
}
