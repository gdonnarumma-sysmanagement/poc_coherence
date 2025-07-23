package com.coherence.examples.demo_todo_list.processor;

import com.coherence.examples.demo_todo_list.model.Task;
import com.tangosol.net.NamedCache;
import com.tangosol.util.InvocableMap;

import java.util.List;

/***
 * Gli Entry Processor ci consentono di eseguire l'elaborazione della griglia dati all'interno del cluster Coherence.
 * È possibile applicare gli Entry Processor a singole chiavi di cache oppure eseguire l'elaborazione parallela su
 * una raccolta di voci di cache (funzionalità map-reduce)
 */
public class CompleteTaskProcessor implements InvocableMap.EntryProcessor<String, Task, Void> {

    @Override
    public Void process(InvocableMap.Entry<String, Task> entry) {
        Task task = entry.getValue();
        task.setCompleted(true);
        entry.setValue(task);

        return null;
    }
}
