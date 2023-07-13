package View;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListDataEvent;

public class ListeModelChoixLayout implements ListModel {

    public ArrayList<String> listeLayout;
    ArrayList<ListDataListener> listeners = new ArrayList<ListDataListener>();
    public ListeModelChoixLayout(String layout[])
    {
        this.listeLayout=new ArrayList<String>();
        refresh(layout);
    }
    @Override
    public void addListDataListener(ListDataListener arg0) {
        this.listeners.add(arg0);
    }
 
    void notifyListeners() {
        ListDataEvent le = new ListDataEvent(this.listeLayout,
            ListDataEvent.CONTENTS_CHANGED, 0, getSize());
        for (int i = 0; i < listeners.size(); i++) {
          ((ListDataListener) listeners.get(i)).contentsChanged(le);
        }
      }
    public void clear() {
        this.listeLayout.clear();
        notifyListeners();
    }

    public void refresh(String[] listeFile) {
        
        this.clear();
        for(String s:listeFile)
        {
            this.listeLayout.add(s);
            
        }
        Collections.sort(this.listeLayout);
        notifyListeners();
      }

    @Override
    public Object getElementAt(int arg0) {
        return this.listeLayout.get(arg0);
    }

    @Override
    public int getSize() {
        return this.listeLayout.size();
    }

    @Override
    public void removeListDataListener(ListDataListener arg0) {
        this.listeners.remove(arg0);
    }

    
}