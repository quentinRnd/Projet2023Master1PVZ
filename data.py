import numpy as np
import pandas as pa
import matplotlib.pyplot as plt

def ajout(a,b):
    for i in range(len(a)):
        a.loc[i,"value"]=a.loc[i,"value"]+b.loc[i,"value"]
    return a

def divise(a,b:int):
    for i in range(len(a)):
        a.loc[i,"value"]=a.loc[i,"value"]/b
    return a

if __name__=="__main__":
    
    fichier=['log/score/log.txt']

    tailleFichier=len(fichier)
    df=pa.read_csv(fichier[0],sep=';')
    for i in range(1,tailleFichier):
        dfaux=pa.read_csv(fichier[i],sep=';')
        df=ajout(df,dfaux)
    df=divise(df,tailleFichier)

        

    

    plt.figure()

    nombretrucaffiche=3

    x = np.arange(nombretrucaffiche)
    y = [df.loc[df["type"] == "test"]["value"].mean(),df.loc[df["type"] == "train"]["value"].mean(),df.loc[df["type"] == "rand"]["value"].mean()]
    
    

    Label=["test ("+str(round(y[0],1))+")","train ("+str(round(y[1],1))+")","rand ("+str(round(y[2],1))+")"]
    pos = np.arange(len(Label))
    plt.xticks(pos, Label)
    plt.bar(x, y, width=1, edgecolor="white", linewidth=0.7,color=["blue","orange","green"])
    labels ="RIEN","P1","P2","P3","P4","P5","P6","P7","P8","P9","P10","P11","P12","P13","P14","P15","D1","D2","D3","D4","D5","D6","D7","D8","D9","D10","D11","D12","D13","D14","D15"
    sizes = [df.loc[df["type"] == "test"]["RIEN"].mean()
            , df.loc[df["type"] == "test"]["P1"].mean()
            , df.loc[df["type"] == "test"]["P2"].mean()
            , df.loc[df["type"] == "test"]["P3"].mean()
            , df.loc[df["type"] == "test"]["P4"].mean()
            , df.loc[df["type"] == "test"]["P5"].mean()
            , df.loc[df["type"] == "test"]["P6"].mean()
            , df.loc[df["type"] == "test"]["P7"].mean()
            , df.loc[df["type"] == "test"]["P8"].mean()
            , df.loc[df["type"] == "test"]["P9"].mean()
            , df.loc[df["type"] == "test"]["P10"].mean()
            , df.loc[df["type"] == "test"]["P11"].mean()
            , df.loc[df["type"] == "test"]["P12"].mean()
            , df.loc[df["type"] == "test"]["P13"].mean()
            , df.loc[df["type"] == "test"]["P14"].mean()
            , df.loc[df["type"] == "test"]["P15"].mean()
            , df.loc[df["type"] == "test"]["D1"].mean()
            , df.loc[df["type"] == "test"]["D2"].mean()
            , df.loc[df["type"] == "test"]["D3"].mean()
            , df.loc[df["type"] == "test"]["D4"].mean()
            , df.loc[df["type"] == "test"]["D5"].mean()
            , df.loc[df["type"] == "test"]["D6"].mean()
            , df.loc[df["type"] == "test"]["D7"].mean()
            , df.loc[df["type"] == "test"]["D8"].mean()
            , df.loc[df["type"] == "test"]["D9"].mean()
            , df.loc[df["type"] == "test"]["D10"].mean()
            , df.loc[df["type"] == "test"]["D11"].mean()
            , df.loc[df["type"] == "test"]["D12"].mean()
            , df.loc[df["type"] == "test"]["D13"].mean()
            , df.loc[df["type"] == "test"]["D14"].mean()
            , df.loc[df["type"] == "test"]["D15"].mean()
            ]
    explode=[]
    for i in range(len(sizes)):
        explode.append(0.1)
    fig, ax = plt.subplots()



    ax.pie(sizes, labels=labels,autopct='%1.1f%%', explode = explode)

    plt.figure()
    

    
    plt.plot(df.loc[df["type"] == "test"]["value"],label="Test",color="blue")
    plt.plot(df.loc[df["type"] == "train"]["value"],label="Training",color="orange")
    plt.plot(df.loc[df["type"] == "rand"]["value"],label="Random",color="green")
    plt.legend()
    #plt.savefig("log_troisieme.png")
    plt.show()
    