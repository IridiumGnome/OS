#include <iostream>
#include <vector>
#include <string>
#include <fstream>
#include <random>

using namespace std;

const int A = -2147483648;
const int B = 2147483647;

void write_csv(string filename, vector<vector<int>> dataset){

    ofstream myFile(filename);

    for(int i = 0; i < 5000000; ++i){
        for(int j = 0; j < 20; ++j){
            myFile << dataset[j][i];
            if(j != 19) myFile << "|";
        }
        myFile << "\n";
    }

    myFile.close();
}

int main() {

    vector<int> vec1;
    vector<int> vec2;
    vector<int> vec3;
    vector<int> vec4;
    vector<int> vec5;
    vector<int> vec6;
    vector<int> vec7;
    vector<int> vec8;
    vector<int> vec9;
    vector<int> vec10;
    vector<int> vec11;
    vector<int> vec12;
    vector<int> vec13;
    vector<int> vec14;
    vector<int> vec15;
    vector<int> vec16;
    vector<int> vec17;
    vector<int> vec18;
    vector<int> vec19;
    vector<int> vec20;

    random_device rseed;
	mt19937 rng(rseed());
	uniform_int_distribution<int> dist(A, B);

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec1.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec2.push_back(num);
	}

    for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec3.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec4.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec5.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec6.push_back(num);
	}
	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec7.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec8.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec9.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec10.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec11.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec12.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec13.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec14.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec15.push_back(num);
	}
	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec16.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec17.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec18.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec19.push_back(num);
	}

	for(int i = 0; i < 5000000; i++){
		int num = dist(rng);
		vec20.push_back(num);
	}

    vector<vector<int>> vec = {{vec1}, {vec2}, {vec3}, {vec4}, {vec5}, {vec6}, {vec7}, {vec8}, {vec9}, {vec10},
                                {vec11}, {vec12}, {vec13}, {vec14}, {vec15}, {vec16}, {vec17}, {vec18}, {vec19}, {vec20}};

    write_csv("input.csv", vec);

    vec1.clear();
    vec2.clear();
    vec3.clear();
    vec4.clear();
    vec5.clear();
    vec6.clear();
    vec7.clear();
    vec8.clear();
    vec9.clear();
    vec10.clear();
    vec11.clear();
    vec12.clear();
    vec13.clear();
    vec14.clear();
    vec15.clear();
    vec16.clear();
    vec17.clear();
    vec18.clear();
    vec19.clear();
    vec20.clear();
    vec.clear();

    return 0;
}
