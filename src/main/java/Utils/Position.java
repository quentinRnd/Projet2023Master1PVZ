package Utils;

import java.io.Serializable;

import Game.EnumActionJeu;

public class Position implements Serializable{
    public long X,Y;

    public Position(long X,Long Y)
    {
        this.X=X;
        this.Y=Y;
    } 
    public Position(int X,int Y)
    {
        this.X=X;
        this.Y=Y;
    } 
    public Position()
    {
        this.X=-1;
        this.Y=-1;
    }
    public Position(Position p)
    {
        this.X=p.X;
        this.Y=p.Y;
    }
    public boolean equals(Position p)
    {
        return this.X==p.X && this.Y==p.Y;
    }

    public static Position PositionActionJeu(EnumActionJeu a)
    {
        switch(a)
        {
            case P1:
            case D1:
            {
                return new Position(0,0);
            }
            case P2:
            case D2:
            {
                return new Position(0,1);
            }
            case P3:
            case D3:
            {
                return new Position(0,2);
            }
            case P4:
            case D4:
            {
                return new Position(0,3);
            }
            case P5:
            case D5:
            {
                return new Position(0,4);
            }
            case P6:
            case D6:
            {
                return new Position(1,0);
            }
            case P7:
            case D7:
            {
                return new Position(1,1);
            }
            case P8:
            case D8:
            {
                return new Position(1,2);
            }
            case P9:
            case D9:
            {
                return new Position(1,3);
            }
            case P10:
            case D10:
            {
                return new Position(1,4);
            }
            case P11:
            case D11:
            {
                return new Position(2,0);
            }
            case P12:
           case D12:
            {
                return new Position(2,1);
            }
            case P13:
            case D13:
            {
                return new Position(2,2);
            }
            case P14:
            case D14:
            {
                return new Position(2,3);
            }
            case P15:
            case D15:
            {
                return new Position(2,4);
            }
            
            case RIEN:
            {
                throw new UnsupportedOperationException("on a pas a aller regarde la position de pas d'action ");
            }
            default:
            {
                throw new UnsupportedOperationException("Erreur");
            }

        }
    }
    public boolean correct()
    {
        return this.X>=0 && this.Y>=0 ;
    }
    public String toString()
    {
        return "("+this.X+","+this.Y+")";
    }
}
