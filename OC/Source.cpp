#include "iostream"
#include "windows.h"

using namespace std;

CRITICAL_SECTION cs1;
CRITICAL_SECTION cs2;

struct param_t
{
	int size;
	double *mass;
};

struct param_p
{
	int size;
	double *mass;
	int lim;
};

DWORD WINAPI Work(LPVOID params)
{
	param_t param = *(param_t*)params;
	EnterCriticalSection(&cs1);
	for (int i = 0; i < param.size - 1; i++)
	{
		for (int j = 0; j < param.size - i - 1; j++)
		{
			if (param.mass[j + 1] < param.mass[j])
			{
				swap(param.mass[j], param.mass[j + 1]);
			}
		}
	}
	cout << "Sorted: ";
	for (int i = 0; i < param.size; i++) {
		cout << param.mass[i] << " ";
	}
	LeaveCriticalSection(&cs1);
	return 0;
}

DWORD WINAPI SumElement(LPVOID params)
{
	param_p param = *(param_p*)params;
	EnterCriticalSection(&cs2);
	WaitCommEvent(Work, NULL, NULL);
	cout << "\n<k: ";
	for (int i = 0; i < param.lim; i++)
	{
		cout << param.mass[i] << " ";
	}

	double summ = 0;
	for (int i = 0; i < param.lim; i++) {
		summ += param.mass[i];
	}
	cout << "\nSummary: " <<summ;
	cout << "\n";
	LeaveCriticalSection(&cs2);
	return 0;
}

void main() {
	InitializeCriticalSection(&cs1);
	InitializeCriticalSection( &cs2 );
	int N;
	cout << "Enter size: ";
	cin >> N;
	double *mass = new double[N];
	cout << "Size: " << N << "\nMass Elements: ";
	for (int i = 0; i < N; i++) {
		mass[i] = (double)rand()/ 500;
		cout << mass[i] << " ";
	}
	int k;
	cout << "\nEnter k: ";
	cin >> k;

	HANDLE hThread;
	DWORD IDThread;
	param_t param = { N, mass };

	HANDLE hThread1;
	DWORD IDThread1;
	param_p param1 = { N, mass, k };

	hThread = CreateThread(NULL, 0, Work, (LPVOID)&param, 0, &IDThread);

	hThread = CreateEvent( NULL, FALSE, FALSE, NULL );
	SetEvent( hThread );

	hThread1 = CreateThread( NULL, 0, SumElement, (LPVOID)&param1, 0, &IDThread1 );

	WaitForSingleObject( hThread, INFINITE );
	WaitForSingleObject(hThread1, INFINITE);
	DeleteCriticalSection(&cs1);
	DeleteCriticalSection( &cs2 );
	system("pause");
}