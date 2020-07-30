#include<bits/stdc++.h>

using namespace std;

string substr(string& s, long long int start, int len){
    string sub;
    for(int i=0;i<len;i++)
        sub.push_back(s[start+i]);
    return sub;
}
void combineAns(string& ans, char s[], int n){
    for(int i=1;i<n;i++){
        if(s[i]=='\0') break;
        if(s[i-1]!='i' && s[i]=='d' && s[i+1]=='=' && s[i+2]=='"'){
            long long int j = i+3;
            while(s[j]!='"'){
                if(('0'<=s[j] && s[j]<='9') || ('A'<=s[j] && s[j]<='Z') || ('a'<=s[j] && s[j]<='z') || s[j]=='.')
                    ans.push_back(s[j]);
                else
                    ans.push_back(' ');
                j++;
            }
            ans.push_back(' ');
            i = j+1;
        }
    }
}
int main()
{
    cout<<"Enter the file name: "<<endl;
    char fileName[20];
    cin>>fileName;
    freopen(fileName,"r",stdin);
    freopen("output.txt","w",stdout);
    long long int i;
    char temp[50000];
    string ans;
    while(cin.getline(temp,50000,EOF)){
        combineAns(ans,temp,50000);
    }
    cout<<ans<<endl;
}