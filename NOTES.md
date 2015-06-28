# Components

## Model

* GraphGridModel: representation of a map
* GraphGridBuildData: used to rebuild a GraphGridModel
* TileMap
* DoorPairEntry: possible combination of one room instance and the map
* RoomPrefab
* RoomInstance

## Generation

* DungeonMachine
* MapGenSolver: performs each generation step
	* Random
	* BestSearch
* PrefabSelector: can be used to alter rooms selected to place
	* Cycle: 1, 2, 3, 1, 2, 3...
	* Probability

## Cache

* DpeFitnessCache
	* Dummy
	* Always
* AInstanceManager
	* BruteInstanceManager: checks all instances
	* PrefabModel: checks just one instance per prefab

## Fitness

* MultiObjectiveFitnessSolver: computes all separated fitnesses
* FitnessCombinator: combine all fitnesses in one
	* Parametrized: A * fa + B * fb + ... N * fn
	* AdaptativeParametrized: decreases last selected best fitness and increases others

More complex ways of fitness computation can be made.

### Computation

* FloydWarshall
* FloodFillGraphMatrix








