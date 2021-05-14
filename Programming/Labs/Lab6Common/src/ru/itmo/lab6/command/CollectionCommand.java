package ru.itmo.lab6.command;

import java.util.ArrayDeque;

import ru.itmo.lab6.collection.Product;

public interface CollectionCommand extends Command
{
	void setCollection(ArrayDeque<Product> deque);
}
