#pragma GCC optimize(2)
#pragma GCC optimize(3)
#pragma GCC optimize("Ofast")
#pragma GCC optimize("inline")
#pragma GCC optimize("-fgcse")
#pragma GCC optimize("-fgcse-lm")
#pragma GCC optimize("-fipa-sra")
#pragma GCC optimize("-ftree-pre")
#pragma GCC optimize("-ftree-vrp")
#pragma GCC optimize("-fpeephole2")
#pragma GCC optimize("-ffast-math")
#pragma GCC optimize("-fsched-spec")
#pragma GCC optimize("unroll-loops")
#pragma GCC optimize("-falign-jumps")
#pragma GCC optimize("-falign-loops")
#pragma GCC optimize("-falign-labels")
#pragma GCC optimize("-fdevirtualize")
#pragma GCC optimize("-fcaller-saves")
#pragma GCC optimize("-fcrossjumping")
#pragma GCC optimize("-fthread-jumps")
#pragma GCC optimize("-funroll-loops")
#pragma GCC optimize("-freorder-blocks")
#pragma GCC optimize("-fschedule-insns")
#pragma GCC optimize("inline-functions")
#pragma GCC optimize("-ftree-tail-merge")
#pragma GCC optimize("-fschedule-insns2")
#pragma GCC optimize("-fstrict-aliasing")
#pragma GCC optimize("-falign-functions")
#pragma GCC optimize("-fcse-follow-jumps")
#pragma GCC optimize("-fsched-interblock")
#pragma GCC optimize("-fpartial-inlining")
#pragma GCC optimize("no-stack-protector")
#pragma GCC optimize("-freorder-functions")
#pragma GCC optimize("-findirect-inlining")
#pragma GCC optimize("-fhoist-adjacent-loads")
#pragma GCC optimize("-frerun-cse-after-loop")
#pragma GCC optimize("inline-small-functions")
#pragma GCC optimize("-finline-small-functions")
#pragma GCC optimize("-ftree-switch-conversion")
#pragma GCC optimize("-foptimize-sibling-calls")
#pragma GCC optimize("-fexpensive-optimizations")
#pragma GCC optimize("inline-functions-called-once")
#pragma GCC optimize("-fdelete-null-pointer-checks")
#pragma GCC optimize("O3")


#include<iostream>
#include<fstream>
#include<queue>
#include<algorithm>
#include<string>
#include<chrono>


using namespace std;

int chunkSize;
int numOfChunks = 1;

string dataPath;

typedef pair<int, int> pi;

// merge the sorted chunks together
void mergeFunction(){

	priority_queue<pi, vector<pi>, greater<pi> > minHeap;

    	ifstream* inFile = new ifstream[numOfChunks];
	inFile->tie(0);

	int tmp = 0;

	// read the 1st value of each sorted chunk
    	for(int i = 1; i <= numOfChunks; i++){
        	string name = "output" + to_string(i) + ".txt";
        	inFile[i-1].open(name);
        	inFile[i-1] >> tmp; 
        	minHeap.push(make_pair(tmp, i - 1));
    	}

    	string name = "output.txt";
    	ofstream outFile(name);
	outFile.tie(0);

	// fill in the output file
    	while(minHeap.size() > 0){
        	pair<int, int> minPair = minHeap.top();
        	minHeap.pop();
        	outFile << minPair.first << "\n";
        	if(inFile[minPair.second] >> tmp)
            		minHeap.push(make_pair(tmp, minPair.second));
    	}

    	// clean
    	for(int i = 1; i <= numOfChunks; i++){
        	inFile[i-1].close();
    	}
    
    	outFile.close();
    	delete[] inFile;

}

// generate sorted chunks
void generateChunks(int *arr, int size){
	
	sort(arr, arr + size);

	string name = "output" + to_string(numOfChunks) + ".txt";
	ofstream outputFile(name);
	outputFile.tie(0);

	for(int i = 0; i < size; i++) outputFile << arr[i] << "\n";
	
	outputFile.close();

}


void splitData(){

	//ifstream inFile("input.txt");
	ifstream inFile(dataPath);
	inFile.tie(0);
 
        int* arr = new int[chunkSize];
 
	int tmp = 0;
        int counter = 0;
        bool notDone = true;

        // split data
	while(inFile >> tmp){
		notDone = true;
                arr[counter++] = tmp;
                if(counter == chunkSize){
                	generateChunks(arr, counter);
                        notDone = false;
                        counter = 0;
                        numOfChunks++;
                }
        }
 
        if(notDone) generateChunks(arr, counter);
        else numOfChunks--;
 
	// clean
        inFile.close();
        delete[]arr;

}

int main(int argc, char** argv){

	ios::sync_with_stdio(false);
	cout.tie(0);

	if(argc >= 3){
	       	chunkSize = stoi(argv[1]);
		dataPath = argv[2];
		int n = 1024 * 1024 * 1000 / 12;
		chunkSize = chunkSize * n;
	}
	else{
		cout << "please input the size of RAM and path" << "\n";
		return 0;
	}

	auto start = chrono::steady_clock::now();

	splitData();

	// sort
	if(numOfChunks == 0) cout << "no data" << "\n";
	else{
	       	mergeFunction();
		auto end = chrono::steady_clock::now();
		cout << "Elapsed time in seconds: "
			<< chrono::duration_cast<chrono::seconds>(end-start).count()
			<< "\n";
	}

	return 0;

}
