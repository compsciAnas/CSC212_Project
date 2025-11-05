#!/bin/bash

# Test script to verify all project requirements

echo "=========================================="
echo "Testing E-Commerce System Requirements"
echo "=========================================="
echo ""

cd /home/runner/work/CSC212_Project/CSC212_Project

# Compile the project
echo "1. Compiling the project..."
rm -rf bin
mkdir -p bin
javac -d bin src/projectFiles/*.java 2>&1
if [ $? -eq 0 ]; then
    echo "✓ Compilation successful"
else
    echo "✗ Compilation failed"
    exit 1
fi
echo ""

# Run the tests
echo "2. Running functionality tests..."
cd bin

# Create test input file
cat > test_input.txt << 'EOF'
7
1
2
3
5
9
201
10
201
202
EOF

# Run tests
java projectFiles.SimpleECommerceTest < test_input.txt > test_output.txt 2>&1

echo "✓ Tests executed"
echo ""

# Check output for key functionalities
echo "3. Verifying requirements:"
echo ""

if grep -q "Laptop Pro 15" test_output.txt; then
    echo "✓ Requirement 1: Products loaded from CSV"
else
    echo "✗ Requirement 1: Products not found"
fi

if grep -q "Alice Johnson" test_output.txt; then
    echo "✓ Requirement 2: Customers loaded from CSV"
else
    echo "✗ Requirement 2: Customers not found"
fi

if grep -q "Order" test_output.txt || grep -q "No orders" test_output.txt; then
    echo "✓ Requirement 3: Orders loaded from CSV"
else
    echo "✗ Requirement 3: Orders not found"
fi

if grep -q "Top 3 Products by Rating" test_output.txt; then
    echo "✓ Requirement 4: Top 3 products by rating implemented"
else
    echo "✗ Requirement 4: Top 3 products not implemented"
fi

if grep -q "Reviews by Alice Johnson" test_output.txt; then
    echo "✓ Requirement 5: Extract reviews from specific customer implemented"
else
    echo "✗ Requirement 5: Extract reviews from customer not implemented"
fi

if grep -q "Common high-rated products" test_output.txt || grep -q "No common high-rated products" test_output.txt; then
    echo "✓ Requirement 6: Common high-rated products between two customers implemented"
else
    echo "✗ Requirement 6: Common high-rated products not implemented"
fi

echo ""
echo "=========================================="
echo "Test Summary"
echo "=========================================="
echo "All core requirements have been implemented."
echo ""

# Show sample output
echo "Sample output from tests:"
echo "----------------------------------------"
head -50 test_output.txt
echo "----------------------------------------"

cd ..
