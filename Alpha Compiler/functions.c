#include "ex.h"

//already declared in .y
int scopes = 0;
int functionNumber = 1;
int isfunc;

char* str;
int error = 0;
//int FuncName = 0;

void printios(){
    int i;
    SymbolTableEntry *a;
    printf("\nprinting hash table... \n");
    for (i=0;i<SIZE;i++){
        a = Hashtable[i];
        while (a != NULL){
            if (a->type == GLOBAL || a->type ==  LOCAL || a->type == FORMAL){
                 printf("type:%d isactive:%d name:%s scope:%d line:%d space:%d offset:%d\n",a->type,a->isActive,a->value.varVal->name,a->value.varVal->scope,a->value.varVal->line,a->space,a->offset);
                 a = a->next;
            }else{
                printf("type:%d isactive:%d name:%s scope:%d line:%d space:%d offset:%d totallocals:%d \n",a->type,a->isActive,a->value.funcVal->name,a->value.funcVal->scope,a->value.funcVal->line,a->space,a->offset, a->value.funcVal->total_locals);
                a = a->next;
            }
        }
    }
}   
 
 
void printlist(){
    SymbolTableEntry *a = Hashtable[0],*temp;
    while (a != NULL){
        if (a->type == GLOBAL || a->type ==  LOCAL || a->type == FORMAL){
           // printf("gia scope iso me %d exw ta eksh: \n",a->value.varVal->scope);
        }else{
            //printf("gia scope iso me %d exw ta eksh: \n",a->value.funcVal->scope);
        }
        temp = a;
        while (temp != NULL){
            if (temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
                 printf("%d %d %s scope = %d %d\n",temp->type,temp->isActive,temp->value.varVal->name,temp->value.varVal->scope,temp->value.varVal->line);
            }else{
                printf("%d %d %s scope = %d %d\n",temp->type,temp->isActive,temp->value.funcVal->name,temp->value.funcVal->scope,temp->value.funcVal->line);
            }
            temp = temp->next_same_scope;
        }
         a = a->next;
    }
}   
         
int Hashkey(enum SymbolTableType  typos,int scope){
    int r;
    r = (typos + scope*10)%SIZE + 1;
    return r;
}
 
 
 
int search(int k,enum SymbolTableType  typos){
    SymbolTableEntry *a;
    a = Hashtable[0];
    while (a != NULL){
        if (typos == GLOBAL || typos ==  LOCAL || typos == FORMAL){
            if (a->value.varVal->scope == k){
                return 1;
            }
        }else{
            if (a->value.funcVal->scope == k){
                return 1;
            }
        }
        a = a->next;
    }
    return 0;
}
 
void NextScope(int k,SymbolTableEntry *a){
   
    SymbolTableEntry *temp = Hashtable[0];
    while (temp != NULL){
        //printf("mpeeeee \n");
        if (temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
            if (temp->value.varVal->scope == k){
                //printf("mpeeeee gia scope = %d \n",k);
                while (temp->next_same_scope != NULL){
                    temp = temp->next_same_scope;
                }
                temp->next_same_scope = a;
                break;
            }
        }else{
             
            if (temp->value.funcVal->scope == k){
               // printf("yyooooo \n");
                //printf("mpeeeee gia scope = %d \n",k);
                while (temp->next_same_scope != NULL){
                    temp = temp->next_same_scope;
                }
                temp->next_same_scope = a;
                break;
            }
        }
        temp = temp->next;
    }
}
 
 
 
SymbolTableEntry* InsertHas(int isctive,enum SymbolTableType  typos,const char *name,unsigned int scope,unsigned int line){
    SymbolTableEntry *a,*b,*c;
    Variable *o;
    Function *q;
    int hashkey,p;
    p = search(scope,typos);
    if (p == 0){
         
        a = Hashtable[0];
        if (Hashtable[0] == NULL){
            //printf("mphka gia scope == %d \n",scope);
            Hashtable[0] =  malloc(sizeof(struct SymbolTableEntry));
            Hashtable[0]->isActive = isctive;
            Hashtable[0]->type = typos;
            Hashtable[0]->next = NULL;
            Hashtable[0]->next_same_scope = NULL;
            if (typos == GLOBAL || typos ==  LOCAL || typos == FORMAL){
                o = malloc(sizeof(struct Variable));
                o->name = name;
                o->scope = scope;
                o->line = line;
                Hashtable[0]->value.varVal = o;
				///
				return Hashtable[0];
            }else{
                q = malloc(sizeof(struct Function));
                q->name = name;
                q->scope = scope;
                q->line = line;
                Hashtable[0]->value.funcVal = q;
				///
				return Hashtable[0];
            }
        }else{
            a = Hashtable[0];
            while (a->next != NULL){
                a = a->next;
            }
            a->next = malloc(sizeof(struct SymbolTableEntry));
            a = a->next;
            a->isActive = isctive;
            a->type = typos;
            a->next_same_scope = NULL;
            if (typos == GLOBAL || typos ==  LOCAL || typos == FORMAL){
                o = malloc(sizeof(struct Variable));
                o->name = name;
                o->scope = scope;
                o->line = line;
                a->value.varVal = o;
				///
				return a;
            }else{
                q = malloc(sizeof(struct Function));
                q->name = name;
                q->scope = scope;
                q->line = line;
                a->value.funcVal = q;
				///
				return a;
            }
        }
    }else{  
            hashkey = Hashkey(typos,scope);
            if (Hashtable[hashkey] == NULL){
                b = malloc(sizeof(struct SymbolTableEntry));
                b->isActive = isctive;
                b->type = typos;
                b->next = NULL;
                b->next_same_scope = NULL;
                Hashtable[hashkey] = malloc(sizeof(struct SymbolTableEntry));
                if (typos == GLOBAL || typos ==  LOCAL || typos == FORMAL){
                    o = malloc(sizeof(struct Variable));
                    o->name = name;
                    o->scope = scope;
                    o->line = line;
                    b->value.varVal = o;
                    Hashtable[hashkey] = b;
					
                     
                }else{
                    q = malloc(sizeof(struct Function));
                    q->name = name;
                    q->scope = scope;
                    q->line = line;
                    b->value.funcVal = q;
                    Hashtable[hashkey] = b;
                }
                NextScope(scope,Hashtable[hashkey]);
                 
				 
				 ///
				return Hashtable[hashkey];
				
            }else{
                a = Hashtable[hashkey];
                while (a->next != NULL){
                    a=a->next;
                }
                a->next = malloc(sizeof(SymbolTableEntry));
                a = a->next;
                a->isActive = isctive;
                a->type = typos;
                a->next_same_scope = NULL;
                if (typos == GLOBAL || typos ==  LOCAL || typos == FORMAL){
                    o = malloc(sizeof(struct Variable));
                    o->name = name;
                    o->scope = scope;
                    o->line = line;
                    a->value.varVal = o;
                }else{
                    q = malloc(sizeof(struct Function));
                    q->name = name;
                    q->scope = scope;
                    q->line = line;
                    a->value.funcVal = q;
                }
                NextScope(scope,a);
				///
				return a;
         
            }
        }
}
     
 
int Lookup(int scope,char *onoma){
    
     SymbolTableEntry *temp = Hashtable[0];

     while(temp!=NULL){
        if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
            if (temp->value.varVal->scope == scope ){
               break;
            }
        }else {
            if (temp->value.funcVal->scope == scope){
                break;
            }
        }
        temp = temp->next;
     }

     while(temp!=NULL){
        if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
            if (!strcmp(temp->value.varVal->name , onoma )){
            //printf("found it!\n");
               return 1;
            }
        }else {
            if (!strcmp(temp->value.funcVal->name ,onoma )){
              //  printf("found it!\n");
                return 1;
            }
        }
        temp = temp->next_same_scope;
     }
     //printf("didn't find it!\n");
     return 0;
 
}

int lookupIfFunc(char *onoma){
    
    SymbolTableEntry *temp = Hashtable[0];
    SymbolTableEntry *temp1;

            while(temp!=NULL){
                if(temp->type == LIBFUNC || temp->type ==  USERFUNC){
                    if(!strcmp(temp->value.funcVal->name ,onoma)){
                        return 1;
                    }       
                }
                
                    temp1 = temp->next_same_scope;
                    while(temp1!=NULL){
                        if(temp1->type == LIBFUNC || temp1->type ==  USERFUNC){
                            if(!strcmp(temp1->value.funcVal->name ,onoma)){
                                return 1;
                            }
                        }
                        temp1 = temp1->next_same_scope;
                    }
                
                temp = temp->next;   
            }
    return 0;
}

int lookupIfFunc2(char *onoma){
    isfunc = 0;
    SymbolTableEntry *temp = Hashtable[0];
    SymbolTableEntry *temp1;

            while(temp!=NULL){
                if(temp->type == LIBFUNC || temp->type ==  USERFUNC){
                    if(!strcmp(temp->value.funcVal->name ,onoma)){
                        return 1;
                    }       
                }
            
                    temp1 = temp->next_same_scope;
                    while(temp1!=NULL){
                        if(temp1->type == LIBFUNC || temp1->type ==  USERFUNC){
                            if(!strcmp(temp1->value.funcVal->name ,onoma)){
                                return 1;
                            }
                        }
                        temp1 = temp1->next_same_scope;
                    }
                
                temp = temp->next;   
            }
            
    return 0;
}


int lookupIfForm (char * onoma){
    SymbolTableEntry *temp = Hashtable[0];
    SymbolTableEntry *temp1;

            while(temp!=NULL){
                if(temp->type == FORMAL || temp->type == GLOBAL){
                    if(!strcmp(temp->value.varVal->name ,onoma)){
                        return 1;
                    }       
                }
            
                    temp1 = temp->next_same_scope;
                    while(temp1!=NULL){
                        if(temp1->type == FORMAL || temp->type == GLOBAL){
                            if(!strcmp(temp1->value.varVal->name ,onoma)){
                                return 1;
                            }
                        }
                        temp1 = temp1->next_same_scope;
                    }
                
                temp = temp->next;   
            }
            
    return 0;
}

void lookupAndHide(int scope){

    SymbolTableEntry *temp = Hashtable[0];
    SymbolTableEntry *temp2;
     while(temp!=NULL){
        if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
            if (temp->value.varVal->scope == scope ){
               // temp->isActive = 0;
                temp2 = temp;
            }
        }else {
            if (temp->value.funcVal->scope == scope){

              //  temp->isActive = 0;
                temp2 = temp;
            }
        }
        temp = temp->next;
     }

     while(temp2!=NULL){

        temp2->isActive = 0;
        temp2 = temp2 -> next_same_scope;
     }
printf("\n");
}

int lookupIsActiveInScope(int scope,char *onoma){
    SymbolTableEntry *temp = Hashtable[0];
    SymbolTableEntry *temp1;


    while(temp!=NULL){
        if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
            if (temp->value.varVal->scope == scope ){
                temp1 = temp;
            }
        }else {
            if (temp->value.funcVal->scope == scope){
                temp1 = temp;
            }
        }
        temp = temp->next;
     }
     
    while(temp1!=NULL){
        if(temp1->type == GLOBAL || temp1->type ==  LOCAL || temp1->type == FORMAL){
                if(!strcmp(temp1->value.varVal->name ,onoma)){
                    if(temp1->isActive == 0){
                        return 0;
                    }
                    else if(temp1->isActive == 1){
                        return 1;
                    }
                }}
        else if (temp1->type == USERFUNC || temp1->type == LIBFUNC){
            if(!strcmp(temp1->value.funcVal->name ,onoma)){
                    if(temp1->isActive == 0){
                        return 0;
                    }
                    else if(temp1->isActive == 1){
                        return 1;
                    }
            }
        }

        temp1 = temp1->next_same_scope;
    
    }

    return -1;
}

int checkLibraryFunction(char* name){
	if(strcmp(name, "print")==0|
		strcmp(name, "input")==0|
		strcmp(name, "objectmemberkeys")==0|
		strcmp(name, "objecttotalmembers")==0|
		strcmp(name, "objectcopy")==0|
		strcmp(name, "totalarguments")==0|
		strcmp(name, "argument")==0|
		strcmp(name, "typeof")==0|
		strcmp(name, "strtonum")==0|
		strcmp(name, "sqrt")==0|
		strcmp(name, "cos")==0|
		strcmp(name, "sin")==0){
			printf("Cannot declare library function\n");
			return 0;
	}
	return 1;
}
char* functionGenerateName(){
   
    
    char* functionString = (char*)malloc(sizeof(char)*100);
    char* functionName = (char*)malloc(sizeof(char)*100);
    
    sprintf(functionString, "%d", functionNumber);
    strcpy(functionName, "_f");
    strcat(functionName, functionString);
    functionNumber++;

    return functionName;
}
void insertLibraryFunctions(){
	InsertHas(1,LIBFUNC,"print",0,0);
	InsertHas(1,LIBFUNC,"input",0,0);
	InsertHas(1,LIBFUNC,"objectmemberkeys",0,0);
	InsertHas(1,LIBFUNC,"objecttotalmembers",0,0);
	InsertHas(1,LIBFUNC,"objectcopy",0,0);
	InsertHas(1,LIBFUNC,"totalarguments",0,0);
	InsertHas(1,LIBFUNC,"argument",0,0);
	InsertHas(1,LIBFUNC,"typeof",0,0);
	InsertHas(1,LIBFUNC,"strtonum",0,0);
	InsertHas(1,LIBFUNC,"sqrt",0,0);
	InsertHas(1,LIBFUNC,"cos",0,0);
	InsertHas(1,LIBFUNC,"sin",0,0);
}

int checkAccess(int scope){
    
    
    SymbolTableEntry *temp = Hashtable[0];
    SymbolTableEntry *temp2;

     while(temp!=NULL){
        if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
            if (temp->value.varVal->scope == scope ){
               // temp->isActive = 0;
                temp2 = temp;
            }
        }else {
            if (temp->value.funcVal->scope == scope){
              //  temp->isActive = 0;
                temp2 = temp;
            }
        }
        temp = temp->next;
     }

     while(temp2!=NULL){
        if((temp2->type == USERFUNC || temp2->type ==  LIBFUNC)){
            return 0; //paremvaletai synarthsh
        }
        temp2 = temp2->next_same_scope;
     }
     return 1;
 
}

int lookupIsActiveinScopeSameName(int scope,char *onoma){
    SymbolTableEntry *temp = Hashtable[0];
    SymbolTableEntry *temp1;


    while(temp!=NULL){
        if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
            if (temp->value.varVal->scope == scope ){
                temp1 = temp;
            }
        }else {
            if (temp->value.funcVal->scope == scope){
                temp1 = temp;
            }
        }
        temp = temp->next;
     }
     
    while(temp1!=NULL){
        if(temp1->type == GLOBAL || temp1->type ==  LOCAL || temp1->type == FORMAL){
                if(!strcmp(temp1->value.varVal->name ,onoma)){
                    if(temp1->isActive == 0){
                    }
                    else if(temp1->isActive == 1){
                        return 1;
                    }
                }}
        else if (temp1->type == USERFUNC || temp1->type == LIBFUNC){
            if(!strcmp(temp1->value.funcVal->name ,onoma)){
                    if(temp1->isActive == 0){
                    }
                    else if(temp1->isActive == 1){
                        return 1;
                    }
            }
        }

        temp1 = temp1->next_same_scope;
    
    }

    return 0;
}

int lookupIsActive (int scope,char *onoma){
    SymbolTableEntry *temp = Hashtable[0];
    SymbolTableEntry *temp1; 

     while(temp!=NULL){
                if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
                    if(!strcmp(temp->value.varVal->name ,onoma)){
                        if(temp->isActive == 0){
                            return 0;
                        }
                        else{
                            return 1;
                        }
                    }       
                }
                else{
                    if(!strcmp(temp->value.funcVal->name ,onoma)){
                        if(temp->isActive == 0){
                            return 0;
                        }
                        else{
                            return 1;
                        }
                    }  
                }
               // else{
                    temp1 = temp->next_same_scope;
                    while(temp1!=NULL){
                        if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
                            if(!strcmp(temp1->value.varVal->name ,onoma)){
                                if(temp->isActive == 0){
                                    return 0;
                                }
                                else{
                                    return 1;
                                 }
                            }
                        }
                        else{
                            if(!strcmp(temp1->value.funcVal->name ,onoma)){
                                if(temp->isActive == 0){
                                    return 0;
                                }
                                else{
                                    return 1;
                                }
                            }
                        }
                        
                    temp1 = temp1->next_same_scope;
                    }
                    temp = temp->next; 
    }
     return -1;
}

int lookupIsActiveScopesSameName (int scope,char *onoma){
    SymbolTableEntry *temp = Hashtable[0];
    SymbolTableEntry *temp1; 

     while(temp!=NULL){
                if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
                    if(!strcmp(temp->value.varVal->name ,onoma)){
                        if(temp->isActive == 0){
                        }
                        else{
                            return 1;
                        }
                    }       
                }
                else{
                    if(!strcmp(temp->value.funcVal->name ,onoma)){
                        if(temp->isActive == 0){
                        }
                        else{
                            return 1;
                        }
                    }  
                }
               // else{
                    temp1 = temp->next_same_scope;
                    while(temp1!=NULL){
                        if(temp->type == GLOBAL || temp->type ==  LOCAL || temp->type == FORMAL){
                            if(!strcmp(temp1->value.varVal->name ,onoma)){
                                if(temp->isActive == 0){
                                }
                                else{
                                    return 1;
                                 }
                            }
                        }
                        else{
                            if(!strcmp(temp1->value.funcVal->name ,onoma)){
                                if(temp->isActive == 0){
                                }
                                else{
                                    return 1;
                                }
                            }
                        }
                        
                    temp1 = temp1->next_same_scope;
                    }
                    temp = temp->next; 
    }
     return 0;
}